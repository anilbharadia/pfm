package com.anil.pfm.service.impl;

import com.anil.pfm.service.MyAccountService;
import com.anil.pfm.repository.MyAccountRepository;
import com.anil.pfm.service.dto.MyAccountDTO;
import com.anil.pfm.service.mapper.MyAccountMapper;
import com.anil.pfm.tx.domain.MyAccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MyAccount.
 */
@Service
@Transactional
public class MyAccountServiceImpl implements MyAccountService{

    private final Logger log = LoggerFactory.getLogger(MyAccountServiceImpl.class);

    private final MyAccountRepository myAccountRepository;

    private final MyAccountMapper myAccountMapper;

    public MyAccountServiceImpl(MyAccountRepository myAccountRepository, MyAccountMapper myAccountMapper) {
        this.myAccountRepository = myAccountRepository;
        this.myAccountMapper = myAccountMapper;
    }

    /**
     * Save a myAccount.
     *
     * @param myAccountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MyAccountDTO save(MyAccountDTO myAccountDTO) {
        log.debug("Request to save MyAccount : {}", myAccountDTO);
        MyAccount myAccount = myAccountMapper.toEntity(myAccountDTO);
        myAccount = myAccountRepository.save(myAccount);
        return myAccountMapper.toDto(myAccount);
    }

    /**
     *  Get all the myAccounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MyAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MyAccounts");
        return myAccountRepository.findAll(pageable)
            .map(myAccountMapper::toDto);
    }

    /**
     *  Get one myAccount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MyAccountDTO findOne(Long id) {
        log.debug("Request to get MyAccount : {}", id);
        MyAccount myAccount = myAccountRepository.findOne(id);
        return myAccountMapper.toDto(myAccount);
    }

    /**
     *  Delete the  myAccount by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyAccount : {}", id);
        myAccountRepository.delete(id);
    }
}
