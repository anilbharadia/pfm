package com.anil.pfm.mf.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anil.pfm.mf.service.dto.CreateMFInvestmentVM;
import com.anil.pfm.mf.service.dto.MFInvestmentDTO;
import com.anil.pfm.mf.service.dto.UpdateMFInvestmentVM;

/**
 * Service Interface for managing MFInvestment.
 */
public interface MFInvestmentService {

    /**
     * Save a mFInvestment.
     *
     * @param mFInvestmentDTO the entity to save
     * @return the persisted entity
     */
    MFInvestmentDTO save(CreateMFInvestmentVM vm);
    
    MFInvestmentDTO update(UpdateMFInvestmentVM vm);

    /**
     *  Get all the mFInvestments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MFInvestmentDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mFInvestment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MFInvestmentDTO findOne(Long id);

    /**
     *  Delete the "id" mFInvestment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
