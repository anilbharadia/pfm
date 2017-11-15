package com.anil.pfm.service.impl;

import com.anil.pfm.service.FixedDepositService;
import com.anil.pfm.domain.FixedDeposit;
import com.anil.pfm.repository.FixedDepositRepository;
import com.anil.pfm.service.dto.FixedDepositDTO;
import com.anil.pfm.service.mapper.FixedDepositMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FixedDeposit.
 */
@Service
@Transactional
public class FixedDepositServiceImpl implements FixedDepositService{

    private final Logger log = LoggerFactory.getLogger(FixedDepositServiceImpl.class);

    private final FixedDepositRepository fixedDepositRepository;

    private final FixedDepositMapper fixedDepositMapper;

    public FixedDepositServiceImpl(FixedDepositRepository fixedDepositRepository, FixedDepositMapper fixedDepositMapper) {
        this.fixedDepositRepository = fixedDepositRepository;
        this.fixedDepositMapper = fixedDepositMapper;
    }

    /**
     * Save a fixedDeposit.
     *
     * @param fixedDepositDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FixedDepositDTO save(FixedDepositDTO fixedDepositDTO) {
        log.debug("Request to save FixedDeposit : {}", fixedDepositDTO);
        FixedDeposit fixedDeposit = fixedDepositMapper.toEntity(fixedDepositDTO);
        fixedDeposit = fixedDepositRepository.save(fixedDeposit);
        return fixedDepositMapper.toDto(fixedDeposit);
    }

    /**
     *  Get all the fixedDeposits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FixedDepositDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FixedDeposits");
        return fixedDepositRepository.findAll(pageable)
            .map(fixedDepositMapper::toDto);
    }

    /**
     *  Get one fixedDeposit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FixedDepositDTO findOne(Long id) {
        log.debug("Request to get FixedDeposit : {}", id);
        FixedDeposit fixedDeposit = fixedDepositRepository.findOne(id);
        return fixedDepositMapper.toDto(fixedDeposit);
    }

    /**
     *  Delete the  fixedDeposit by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FixedDeposit : {}", id);
        fixedDepositRepository.delete(id);
    }
}
