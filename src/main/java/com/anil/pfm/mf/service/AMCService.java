package com.anil.pfm.mf.service;

import com.anil.pfm.service.dto.AMCDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AMC.
 */
public interface AMCService {

    /**
     * Save a aMC.
     *
     * @param aMCDTO the entity to save
     * @return the persisted entity
     */
    AMCDTO save(AMCDTO aMCDTO);

    /**
     *  Get all the aMCS.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AMCDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" aMC.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AMCDTO findOne(Long id);

    /**
     *  Delete the "id" aMC.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
