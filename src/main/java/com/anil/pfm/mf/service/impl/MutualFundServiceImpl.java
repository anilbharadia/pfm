package com.anil.pfm.mf.service.impl;

import com.anil.pfm.mf.domain.MutualFund;
import com.anil.pfm.mf.repository.MutualFundRepository;
import com.anil.pfm.mf.service.MutualFundService;
import com.anil.pfm.mf.service.dto.MutualFundDTO;
import com.anil.pfm.mf.service.mapper.MutualFundMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MutualFund.
 */
@Service
@Transactional
public class MutualFundServiceImpl implements MutualFundService{

    private final Logger log = LoggerFactory.getLogger(MutualFundServiceImpl.class);

    private final MutualFundRepository mutualFundRepository;

    private final MutualFundMapper mutualFundMapper;

    public MutualFundServiceImpl(MutualFundRepository mutualFundRepository, MutualFundMapper mutualFundMapper) {
        this.mutualFundRepository = mutualFundRepository;
        this.mutualFundMapper = mutualFundMapper;
    }

    /**
     * Save a mutualFund.
     *
     * @param mutualFundDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MutualFundDTO save(MutualFundDTO mutualFundDTO) {
        log.debug("Request to save MutualFund : {}", mutualFundDTO);
        MutualFund mutualFund = mutualFundMapper.toEntity(mutualFundDTO);
        mutualFund = mutualFundRepository.save(mutualFund);
        return mutualFundMapper.toDto(mutualFund);
    }

    /**
     *  Get all the mutualFunds.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MutualFundDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MutualFunds");
        return mutualFundRepository.findAll(pageable)
            .map(mutualFundMapper::toDto);
    }

    /**
     *  Get one mutualFund by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MutualFundDTO findOne(Long id) {
        log.debug("Request to get MutualFund : {}", id);
        MutualFund mutualFund = mutualFundRepository.findOne(id);
        return mutualFundMapper.toDto(mutualFund);
    }

    /**
     *  Delete the  mutualFund by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MutualFund : {}", id);
        mutualFundRepository.delete(id);
    }
}
