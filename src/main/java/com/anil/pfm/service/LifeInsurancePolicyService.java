package com.anil.pfm.service;

import com.anil.pfm.service.dto.LifeInsurancePolicyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LifeInsurancePolicy.
 */
public interface LifeInsurancePolicyService {

    /**
     * Save a lifeInsurancePolicy.
     *
     * @param lifeInsurancePolicyDTO the entity to save
     * @return the persisted entity
     */
    LifeInsurancePolicyDTO save(LifeInsurancePolicyDTO lifeInsurancePolicyDTO);

    /**
     *  Get all the lifeInsurancePolicies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LifeInsurancePolicyDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" lifeInsurancePolicy.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LifeInsurancePolicyDTO findOne(Long id);

    /**
     *  Delete the "id" lifeInsurancePolicy.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
