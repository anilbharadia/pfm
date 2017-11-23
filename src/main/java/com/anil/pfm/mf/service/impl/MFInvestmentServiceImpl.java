package com.anil.pfm.mf.service.impl;

import com.anil.pfm.mf.domain.MFInvestment;
import com.anil.pfm.mf.repository.MFInvestmentRepository;
import com.anil.pfm.mf.service.MFInvestmentService;
import com.anil.pfm.mf.service.mapper.MFInvestmentMapper;
import com.anil.pfm.service.dto.MFInvestmentDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MFInvestment.
 */
@Service
@Transactional
public class MFInvestmentServiceImpl implements MFInvestmentService{

    private final Logger log = LoggerFactory.getLogger(MFInvestmentServiceImpl.class);

    private final MFInvestmentRepository mFInvestmentRepository;

    private final MFInvestmentMapper mFInvestmentMapper;

    public MFInvestmentServiceImpl(MFInvestmentRepository mFInvestmentRepository, MFInvestmentMapper mFInvestmentMapper) {
        this.mFInvestmentRepository = mFInvestmentRepository;
        this.mFInvestmentMapper = mFInvestmentMapper;
    }

    /**
     * Save a mFInvestment.
     *
     * @param mFInvestmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MFInvestmentDTO save(MFInvestmentDTO mFInvestmentDTO) {
        log.debug("Request to save MFInvestment : {}", mFInvestmentDTO);
        MFInvestment mFInvestment = mFInvestmentMapper.toEntity(mFInvestmentDTO);
        mFInvestment = mFInvestmentRepository.save(mFInvestment);
        return mFInvestmentMapper.toDto(mFInvestment);
    }

    /**
     *  Get all the mFInvestments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MFInvestmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MFInvestments");
        return mFInvestmentRepository.findAll(pageable)
            .map(mFInvestmentMapper::toDto);
    }

    /**
     *  Get one mFInvestment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MFInvestmentDTO findOne(Long id) {
        log.debug("Request to get MFInvestment : {}", id);
        MFInvestment mFInvestment = mFInvestmentRepository.findOne(id);
        return mFInvestmentMapper.toDto(mFInvestment);
    }

    /**
     *  Delete the  mFInvestment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MFInvestment : {}", id);
        mFInvestmentRepository.delete(id);
    }
}
