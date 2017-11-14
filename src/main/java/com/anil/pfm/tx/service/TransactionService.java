package com.anil.pfm.tx.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anil.pfm.tx.service.dto.CreateTransactionVM;
import com.anil.pfm.tx.service.dto.FilterTransactionVM;
import com.anil.pfm.tx.service.dto.TransactionDTO;
import com.anil.pfm.tx.service.dto.UpdateTransactionVM;

/**
 * Service Interface for managing Transaction.
 */
public interface TransactionService {

    /**
     * Save a transaction.
     *
     * @param transactionDTO the entity to save
     * @return the persisted entity
     */
    TransactionDTO save(CreateTransactionVM vm);
    
    TransactionDTO update(UpdateTransactionVM vm);

    /**
     *  Get all the transactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TransactionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" transaction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TransactionDTO findOne(Long id);

    /**
     *  Delete the "id" transaction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

	Page<TransactionDTO> filter(FilterTransactionVM vm, Pageable pageable);
}
