package com.anil.pfm.service;

import com.anil.pfm.service.dto.IncomeCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing IncomeCategory.
 */
public interface IncomeCategoryService {

    /**
     * Save a incomeCategory.
     *
     * @param incomeCategoryDTO the entity to save
     * @return the persisted entity
     */
    IncomeCategoryDTO save(IncomeCategoryDTO incomeCategoryDTO);

    /**
     *  Get all the incomeCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<IncomeCategoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" incomeCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    IncomeCategoryDTO findOne(Long id);

    /**
     *  Delete the "id" incomeCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
