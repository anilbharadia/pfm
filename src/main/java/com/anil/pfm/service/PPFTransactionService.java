package com.anil.pfm.service;

import com.anil.pfm.service.dto.PPFTransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PPFTransaction.
 */
public interface PPFTransactionService {

    /**
     * Save a pPFTransaction.
     *
     * @param pPFTransactionDTO the entity to save
     * @return the persisted entity
     */
    PPFTransactionDTO save(PPFTransactionDTO pPFTransactionDTO);

    /**
     *  Get all the pPFTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PPFTransactionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pPFTransaction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PPFTransactionDTO findOne(Long id);

    /**
     *  Delete the "id" pPFTransaction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
