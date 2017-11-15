package com.anil.pfm.service;

import com.anil.pfm.service.dto.RecurringDepositDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RecurringDeposit.
 */
public interface RecurringDepositService {

    /**
     * Save a recurringDeposit.
     *
     * @param recurringDepositDTO the entity to save
     * @return the persisted entity
     */
    RecurringDepositDTO save(RecurringDepositDTO recurringDepositDTO);

    /**
     *  Get all the recurringDeposits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RecurringDepositDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" recurringDeposit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RecurringDepositDTO findOne(Long id);

    /**
     *  Delete the "id" recurringDeposit.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
