package com.anil.pfm.service;

import com.anil.pfm.service.dto.MFInvestmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    MFInvestmentDTO save(MFInvestmentDTO mFInvestmentDTO);

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
