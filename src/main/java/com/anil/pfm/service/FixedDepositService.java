package com.anil.pfm.service;

import com.anil.pfm.service.dto.FixedDepositDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FixedDeposit.
 */
public interface FixedDepositService {

    /**
     * Save a fixedDeposit.
     *
     * @param fixedDepositDTO the entity to save
     * @return the persisted entity
     */
    FixedDepositDTO save(FixedDepositDTO fixedDepositDTO);

    /**
     *  Get all the fixedDeposits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FixedDepositDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" fixedDeposit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FixedDepositDTO findOne(Long id);

    /**
     *  Delete the "id" fixedDeposit.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
