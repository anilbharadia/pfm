package com.anil.pfm.service.impl;

import com.anil.pfm.service.PPFAccountService;
import com.anil.pfm.domain.PPFAccount;
import com.anil.pfm.repository.PPFAccountRepository;
import com.anil.pfm.service.dto.PPFAccountDTO;
import com.anil.pfm.service.mapper.PPFAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PPFAccount.
 */
@Service
@Transactional
public class PPFAccountServiceImpl implements PPFAccountService{

    private final Logger log = LoggerFactory.getLogger(PPFAccountServiceImpl.class);

    private final PPFAccountRepository pPFAccountRepository;

    private final PPFAccountMapper pPFAccountMapper;

    public PPFAccountServiceImpl(PPFAccountRepository pPFAccountRepository, PPFAccountMapper pPFAccountMapper) {
        this.pPFAccountRepository = pPFAccountRepository;
        this.pPFAccountMapper = pPFAccountMapper;
    }

    /**
     * Save a pPFAccount.
     *
     * @param pPFAccountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PPFAccountDTO save(PPFAccountDTO pPFAccountDTO) {
        log.debug("Request to save PPFAccount : {}", pPFAccountDTO);
        PPFAccount pPFAccount = pPFAccountMapper.toEntity(pPFAccountDTO);
        pPFAccount = pPFAccountRepository.save(pPFAccount);
        return pPFAccountMapper.toDto(pPFAccount);
    }

    /**
     *  Get all the pPFAccounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PPFAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PPFAccounts");
        return pPFAccountRepository.findAll(pageable)
            .map(pPFAccountMapper::toDto);
    }

    /**
     *  Get one pPFAccount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PPFAccountDTO findOne(Long id) {
        log.debug("Request to get PPFAccount : {}", id);
        PPFAccount pPFAccount = pPFAccountRepository.findOne(id);
        return pPFAccountMapper.toDto(pPFAccount);
    }

    /**
     *  Delete the  pPFAccount by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PPFAccount : {}", id);
        pPFAccountRepository.delete(id);
    }
}
