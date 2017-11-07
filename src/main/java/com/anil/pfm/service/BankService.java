package com.anil.pfm.service;

import com.anil.pfm.service.dto.BankDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Bank.
 */
public interface BankService {

    /**
     * Save a bank.
     *
     * @param bankDTO the entity to save
     * @return the persisted entity
     */
    BankDTO save(BankDTO bankDTO);

    /**
     *  Get all the banks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BankDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" bank.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BankDTO findOne(Long id);

    /**
     *  Delete the "id" bank.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
