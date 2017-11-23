package com.anil.pfm.mf.service.impl;

import com.anil.pfm.mf.domain.AMC;
import com.anil.pfm.mf.repository.AMCRepository;
import com.anil.pfm.mf.service.AMCService;
import com.anil.pfm.mf.service.dto.AMCDTO;
import com.anil.pfm.mf.service.mapper.AMCMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AMC.
 */
@Service
@Transactional
public class AMCServiceImpl implements AMCService{

    private final Logger log = LoggerFactory.getLogger(AMCServiceImpl.class);

    private final AMCRepository aMCRepository;

    private final AMCMapper aMCMapper;

    public AMCServiceImpl(AMCRepository aMCRepository, AMCMapper aMCMapper) {
        this.aMCRepository = aMCRepository;
        this.aMCMapper = aMCMapper;
    }

    /**
     * Save a aMC.
     *
     * @param aMCDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AMCDTO save(AMCDTO aMCDTO) {
        log.debug("Request to save AMC : {}", aMCDTO);
        AMC aMC = aMCMapper.toEntity(aMCDTO);
        aMC = aMCRepository.save(aMC);
        return aMCMapper.toDto(aMC);
    }

    /**
     *  Get all the aMCS.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AMCDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AMCS");
        return aMCRepository.findAll(pageable)
            .map(aMCMapper::toDto);
    }

    /**
     *  Get one aMC by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AMCDTO findOne(Long id) {
        log.debug("Request to get AMC : {}", id);
        AMC aMC = aMCRepository.findOne(id);
        return aMCMapper.toDto(aMC);
    }

    /**
     *  Delete the  aMC by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AMC : {}", id);
        aMCRepository.delete(id);
    }
}
