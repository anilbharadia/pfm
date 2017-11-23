package com.anil.pfm.mf.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anil.pfm.mf.service.dto.MFCategoryDTO;

/**
 * Service Interface for managing MFCategory.
 */
public interface MFCategoryService {

    /**
     * Save a mFCategory.
     *
     * @param mFCategoryDTO the entity to save
     * @return the persisted entity
     */
    MFCategoryDTO save(MFCategoryDTO mFCategoryDTO);

    /**
     *  Get all the mFCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MFCategoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mFCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MFCategoryDTO findOne(Long id);

    /**
     *  Delete the "id" mFCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
