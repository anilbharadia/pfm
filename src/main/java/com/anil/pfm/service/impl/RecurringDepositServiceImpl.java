package com.anil.pfm.service.impl;

import com.anil.pfm.service.RecurringDepositService;
import com.anil.pfm.domain.RecurringDeposit;
import com.anil.pfm.repository.RecurringDepositRepository;
import com.anil.pfm.service.dto.RecurringDepositDTO;
import com.anil.pfm.service.mapper.RecurringDepositMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RecurringDeposit.
 */
@Service
@Transactional
public class RecurringDepositServiceImpl implements RecurringDepositService{

    private final Logger log = LoggerFactory.getLogger(RecurringDepositServiceImpl.class);

    private final RecurringDepositRepository recurringDepositRepository;

    private final RecurringDepositMapper recurringDepositMapper;

    public RecurringDepositServiceImpl(RecurringDepositRepository recurringDepositRepository, RecurringDepositMapper recurringDepositMapper) {
        this.recurringDepositRepository = recurringDepositRepository;
        this.recurringDepositMapper = recurringDepositMapper;
    }

    /**
     * Save a recurringDeposit.
     *
     * @param recurringDepositDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecurringDepositDTO save(RecurringDepositDTO recurringDepositDTO) {
        log.debug("Request to save RecurringDeposit : {}", recurringDepositDTO);
        RecurringDeposit recurringDeposit = recurringDepositMapper.toEntity(recurringDepositDTO);
        recurringDeposit = recurringDepositRepository.save(recurringDeposit);
        return recurringDepositMapper.toDto(recurringDeposit);
    }

    /**
     *  Get all the recurringDeposits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecurringDepositDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecurringDeposits");
        return recurringDepositRepository.findAll(pageable)
            .map(recurringDepositMapper::toDto);
    }

    /**
     *  Get one recurringDeposit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RecurringDepositDTO findOne(Long id) {
        log.debug("Request to get RecurringDeposit : {}", id);
        RecurringDeposit recurringDeposit = recurringDepositRepository.findOne(id);
        return recurringDepositMapper.toDto(recurringDeposit);
    }

    /**
     *  Delete the  recurringDeposit by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecurringDeposit : {}", id);
        recurringDepositRepository.delete(id);
    }
}
