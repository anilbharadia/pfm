package com.anil.pfm.service;

import com.anil.pfm.service.dto.TransactionCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TransactionCategory.
 */
public interface TransactionCategoryService {

    /**
     * Save a transactionCategory.
     *
     * @param transactionCategoryDTO the entity to save
     * @return the persisted entity
     */
    TransactionCategoryDTO save(TransactionCategoryDTO transactionCategoryDTO);

    /**
     *  Get all the transactionCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TransactionCategoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" transactionCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TransactionCategoryDTO findOne(Long id);

    /**
     *  Delete the "id" transactionCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
