package com.anil.pfm.tx.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.anil.pfm.domain.PPFTransaction;
import com.anil.pfm.domain.TransactionType;
import com.anil.pfm.repository.PPFTransactionRepository;
import com.anil.pfm.repository.TransactionRepository;
import com.anil.pfm.service.mapper.TransactionMapper;
import com.anil.pfm.tx.domain.MyAccount;
import com.anil.pfm.tx.domain.Transaction;
import com.anil.pfm.tx.service.TransactionService;
import com.anil.pfm.tx.service.dto.CreateTransactionVM;
import com.anil.pfm.tx.service.dto.FilterTransactionVM;
import com.anil.pfm.tx.service.dto.TransactionDTO;
import com.anil.pfm.tx.service.dto.UpdateTransactionVM;

/**
 * Service Implementation for managing Transaction.
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

	private final TransactionRepository txRepository;

	private final TransactionMapper transactionMapper;
	
	private final PPFTransactionRepository ppfTxRepository;
	
	public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, PPFTransactionRepository ppfTxRepository) {
		this.txRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
		this.ppfTxRepository = ppfTxRepository;
	}

	/**
	 * Save a transaction.
	 *
	 * @param vm
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public TransactionDTO save(CreateTransactionVM vm) {
		log.debug("Request to save Transaction : {}", vm);
		Transaction transaction = transactionMapper.toEntity(vm);

		updateBalance(transaction);

		transaction = txRepository.save(transaction);
		return transactionMapper.toDto(transaction);
	}
	
	@Override
	public TransactionDTO update(UpdateTransactionVM vm) {
		log.debug("Request to update Transaction : {}", vm);
		
		Transaction tx = txRepository.findOne(vm.getId());
		
		transactionMapper.update(vm, tx);

		tx = txRepository.save(tx);
		
		return transactionMapper.toDto(tx);
	}

	private void updateBalance(Transaction tx) {

		MyAccount account = tx.getAccount();
		BigDecimal balance = account.getBalance();

		tx.setOpeningBalance(balance);

		TransactionType type = tx.getTxType();
		BigDecimal amount = tx.getAmount();

		if (type.isExpense() || type.isInvestment()) {
			balance = balance.subtract(amount);
		
		} else if (type.isIncome()) {
			balance = balance.add(amount);
		
		} else if (type.isTransfer()) {
			balance = balance.subtract(amount);
			
			MyAccount transferAcc = tx.getTransferAccount();
			
			transferAcc.setBalance(transferAcc.getBalance().add(amount));
		}
		
		account.setBalance(balance);
		tx.setClosingBalance(balance);
	}

	/**
	 * Get all the transactions.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<TransactionDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Transactions");
		return txRepository.findAll(pageable).map(transactionMapper::toDto);
	}

	/**
	 * Get one transaction by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public TransactionDTO findOne(Long id) {
		log.debug("Request to get Transaction : {}", id);
		Transaction transaction = txRepository.findOne(id);
		return transactionMapper.toDto(transaction);
	}

	/**
	 * Delete the transaction by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Transaction : {}", id);
		
		Transaction tx = txRepository.findOne(id);

		validateDelete(tx);
		
		tx.setAmount(tx.getAmount().negate());
		updateBalance(tx);
		
		txRepository.delete(id);
	}

	private void validateDelete(Transaction tx) {
		
		if (tx.getTxType().isInvestment()) {
			List<PPFTransaction> ppfTxs = ppfTxRepository.findByTransaction_Id(tx.getId());

			if (!CollectionUtils.isEmpty(ppfTxs)) {
				throw new RuntimeException("Cannot Delete Transaction directly, Please delete PPF Transacction to delete this.");
			}			
		}
	}

	@Override
	public Page<TransactionDTO> filter(FilterTransactionVM vm, Pageable pageable) {
		// TODO Auto-generated method stub
		return txRepository.filter(vm, pageable).map(transactionMapper::toDto);
	}
}
