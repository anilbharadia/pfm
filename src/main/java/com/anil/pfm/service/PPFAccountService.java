package com.anil.pfm.service;

import com.anil.pfm.service.dto.PPFAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PPFAccount.
 */
public interface PPFAccountService {

    /**
     * Save a pPFAccount.
     *
     * @param pPFAccountDTO the entity to save
     * @return the persisted entity
     */
    PPFAccountDTO save(PPFAccountDTO pPFAccountDTO);

    /**
     *  Get all the pPFAccounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PPFAccountDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pPFAccount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PPFAccountDTO findOne(Long id);

    /**
     *  Delete the "id" pPFAccount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
