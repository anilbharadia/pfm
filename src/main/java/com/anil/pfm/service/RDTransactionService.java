package com.anil.pfm.service;

import com.anil.pfm.service.dto.RDTransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RDTransaction.
 */
public interface RDTransactionService {

    /**
     * Save a rDTransaction.
     *
     * @param rDTransactionDTO the entity to save
     * @return the persisted entity
     */
    RDTransactionDTO save(RDTransactionDTO rDTransactionDTO);

    /**
     *  Get all the rDTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RDTransactionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" rDTransaction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RDTransactionDTO findOne(Long id);

    /**
     *  Delete the "id" rDTransaction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
