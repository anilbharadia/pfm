package com.anil.pfm.service;

import com.anil.pfm.service.dto.MyAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MyAccount.
 */
public interface MyAccountService {

    /**
     * Save a myAccount.
     *
     * @param myAccountDTO the entity to save
     * @return the persisted entity
     */
    MyAccountDTO save(MyAccountDTO myAccountDTO);

    /**
     *  Get all the myAccounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MyAccountDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" myAccount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MyAccountDTO findOne(Long id);

    /**
     *  Delete the "id" myAccount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
