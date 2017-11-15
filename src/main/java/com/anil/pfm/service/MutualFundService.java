package com.anil.pfm.service;

import com.anil.pfm.service.dto.MutualFundDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MutualFund.
 */
public interface MutualFundService {

    /**
     * Save a mutualFund.
     *
     * @param mutualFundDTO the entity to save
     * @return the persisted entity
     */
    MutualFundDTO save(MutualFundDTO mutualFundDTO);

    /**
     *  Get all the mutualFunds.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MutualFundDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mutualFund.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MutualFundDTO findOne(Long id);

    /**
     *  Delete the "id" mutualFund.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
