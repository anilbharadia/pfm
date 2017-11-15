package com.anil.pfm.service;

import com.anil.pfm.service.dto.LifeInsuranceCompanyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LifeInsuranceCompany.
 */
public interface LifeInsuranceCompanyService {

    /**
     * Save a lifeInsuranceCompany.
     *
     * @param lifeInsuranceCompanyDTO the entity to save
     * @return the persisted entity
     */
    LifeInsuranceCompanyDTO save(LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO);

    /**
     *  Get all the lifeInsuranceCompanies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LifeInsuranceCompanyDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" lifeInsuranceCompany.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LifeInsuranceCompanyDTO findOne(Long id);

    /**
     *  Delete the "id" lifeInsuranceCompany.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
