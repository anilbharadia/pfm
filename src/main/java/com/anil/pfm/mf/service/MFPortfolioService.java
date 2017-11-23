package com.anil.pfm.mf.service;

import com.anil.pfm.service.dto.MFPortfolioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MFPortfolio.
 */
public interface MFPortfolioService {

    /**
     * Save a mFPortfolio.
     *
     * @param mFPortfolioDTO the entity to save
     * @return the persisted entity
     */
    MFPortfolioDTO save(MFPortfolioDTO mFPortfolioDTO);

    /**
     *  Get all the mFPortfolios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MFPortfolioDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mFPortfolio.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MFPortfolioDTO findOne(Long id);

    /**
     *  Delete the "id" mFPortfolio.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
