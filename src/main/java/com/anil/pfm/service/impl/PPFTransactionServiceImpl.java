package com.anil.pfm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anil.pfm.domain.PPFAccount;
import com.anil.pfm.domain.PPFTransaction;
import com.anil.pfm.repository.PPFTransactionRepository;
import com.anil.pfm.repository.TransactionRepository;
import com.anil.pfm.service.PPFTransactionService;
import com.anil.pfm.service.dto.PPFTransactionDTO;
import com.anil.pfm.service.mapper.PPFTransactionMapper;
import com.anil.pfm.tx.domain.Transaction;
import com.anil.pfm.tx.service.TransactionService;
import com.anil.pfm.tx.service.dto.TransactionDTO;


/**
 * Service Implementation for managing PPFTransaction.
 */
@Service
@Transactional
public class PPFTransactionServiceImpl implements PPFTransactionService{

    private final Logger log = LoggerFactory.getLogger(PPFTransactionServiceImpl.class);

    private final PPFTransactionRepository repository;

    private final PPFTransactionMapper mapper;
    
    private final TransactionService txService;
    
    private final TransactionRepository txRepository;

    public PPFTransactionServiceImpl(PPFTransactionRepository pPFTransactionRepository, PPFTransactionMapper pPFTransactionMapper, TransactionService txService, TransactionRepository txRepository) {
        this.repository = pPFTransactionRepository;
        this.mapper = pPFTransactionMapper;
        this.txService = txService;
        this.txRepository = txRepository;
    }

    /**
     * Save a pPFTransaction.
     *
     * @param pPFTransactionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PPFTransactionDTO save(PPFTransactionDTO pPFTransactionDTO) {
        log.debug("Request to save PPFTransaction : {}", pPFTransactionDTO);
        PPFTransaction tx = mapper.toEntity(pPFTransactionDTO);
        
        updateBalance(tx);
        
        tx = repository.save(tx);
        return mapper.toDto(tx);
    }
    
    private void updateBalance(PPFTransaction tx) {

		updatePPFAccountBalance(tx);
		
		updateMyAccountBalance(tx);
	}

	private void updateMyAccountBalance(PPFTransaction ppfTx) {

		TransactionDTO tx = txService.save(mapper.toCreateTransactionVM(ppfTx));
		
		ppfTx.setTransaction(txRepository.findOne(tx.getId()));
		
	}

	private void updatePPFAccountBalance(PPFTransaction tx) {
		PPFAccount account = tx.getAccount();
		account.setBalance(account.getBalance().add(tx.getAmount()));
	}

    /**
     *  Get all the pPFTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PPFTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PPFTransactions");
        return repository.findAll(pageable)
            .map(mapper::toDto);
    }

    /**
     *  Get one pPFTransaction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PPFTransactionDTO findOne(Long id) {
        log.debug("Request to get PPFTransaction : {}", id);
        PPFTransaction pPFTransaction = repository.findOne(id);
        return mapper.toDto(pPFTransaction);
    }

    /**
     *  Delete the  pPFTransaction by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PPFTransaction : {}", id);
        
        PPFTransaction ppfTx = repository.findOne(id);
		
		ppfTx.setAmount(ppfTx.getAmount().negate());
		updatePPFAccountBalance(ppfTx);
		
		Transaction tx = ppfTx.getTransaction();
        
        repository.delete(id);
        
        if (tx != null) {
			txService.delete(tx.getId());
		}
    }
}
