package com.anil.pfm.service.impl;

import com.anil.pfm.service.TransactionService;
import com.anil.pfm.domain.MyAccount;
import com.anil.pfm.domain.Transaction;
import com.anil.pfm.domain.TransactionType;
import com.anil.pfm.repository.TransactionRepository;
import com.anil.pfm.service.mapper.TransactionMapper;
import com.anil.pfm.tx.service.dto.CreateTransactionVM;
import com.anil.pfm.tx.service.dto.FilterTransactionVM;
import com.anil.pfm.tx.service.dto.TransactionDTO;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Transaction.
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

	private final TransactionRepository transactionRepository;

	private final TransactionMapper transactionMapper;

	public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
		this.transactionRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
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

		transaction = transactionRepository.save(transaction);
		return transactionMapper.toDto(transaction);
	}

	private void updateBalance(Transaction transaction) {

		MyAccount account = transaction.getAccount();
		BigDecimal balance = account.getBalance();

		transaction.setOpeningBalance(balance);

		TransactionType type = transaction.getTxType();
		BigDecimal amount = transaction.getAmount();

		if (type.isExpense()) {
			balance = balance.subtract(amount);
		} else if (type.isIncome()) {
			balance = balance.add(amount);
		}
		account.setBalance(balance);
		transaction.setClosingBalance(balance);
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
		return transactionRepository.findAll(pageable).map(transactionMapper::toDto);
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
		Transaction transaction = transactionRepository.findOne(id);
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
		transactionRepository.delete(id);
	}

	@Override
	public TransactionDTO update(TransactionDTO vm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<TransactionDTO> filter(FilterTransactionVM vm, Pageable pageable) {
		// TODO Auto-generated method stub
		return transactionRepository.filter(vm, pageable).map(transactionMapper::toDto);
	}
}
