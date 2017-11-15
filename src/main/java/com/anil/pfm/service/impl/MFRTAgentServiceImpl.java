package com.anil.pfm.service.impl;

import com.anil.pfm.service.MFRTAgentService;
import com.anil.pfm.domain.MFRTAgent;
import com.anil.pfm.repository.MFRTAgentRepository;
import com.anil.pfm.service.dto.MFRTAgentDTO;
import com.anil.pfm.service.mapper.MFRTAgentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MFRTAgent.
 */
@Service
@Transactional
public class MFRTAgentServiceImpl implements MFRTAgentService{

    private final Logger log = LoggerFactory.getLogger(MFRTAgentServiceImpl.class);

    private final MFRTAgentRepository mFRTAgentRepository;

    private final MFRTAgentMapper mFRTAgentMapper;

    public MFRTAgentServiceImpl(MFRTAgentRepository mFRTAgentRepository, MFRTAgentMapper mFRTAgentMapper) {
        this.mFRTAgentRepository = mFRTAgentRepository;
        this.mFRTAgentMapper = mFRTAgentMapper;
    }

    /**
     * Save a mFRTAgent.
     *
     * @param mFRTAgentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MFRTAgentDTO save(MFRTAgentDTO mFRTAgentDTO) {
        log.debug("Request to save MFRTAgent : {}", mFRTAgentDTO);
        MFRTAgent mFRTAgent = mFRTAgentMapper.toEntity(mFRTAgentDTO);
        mFRTAgent = mFRTAgentRepository.save(mFRTAgent);
        return mFRTAgentMapper.toDto(mFRTAgent);
    }

    /**
     *  Get all the mFRTAgents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MFRTAgentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MFRTAgents");
        return mFRTAgentRepository.findAll(pageable)
            .map(mFRTAgentMapper::toDto);
    }

    /**
     *  Get one mFRTAgent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MFRTAgentDTO findOne(Long id) {
        log.debug("Request to get MFRTAgent : {}", id);
        MFRTAgent mFRTAgent = mFRTAgentRepository.findOne(id);
        return mFRTAgentMapper.toDto(mFRTAgent);
    }

    /**
     *  Delete the  mFRTAgent by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MFRTAgent : {}", id);
        mFRTAgentRepository.delete(id);
    }
}
