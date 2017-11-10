package com.anil.pfm.service;

import com.anil.pfm.service.dto.ExpenseCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ExpenseCategory.
 */
public interface ExpenseCategoryService {

    /**
     * Save a expenseCategory.
     *
     * @param expenseCategoryDTO the entity to save
     * @return the persisted entity
     */
    ExpenseCategoryDTO save(ExpenseCategoryDTO expenseCategoryDTO);

    /**
     *  Get all the expenseCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ExpenseCategoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" expenseCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ExpenseCategoryDTO findOne(Long id);

    /**
     *  Delete the "id" expenseCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
