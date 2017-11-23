package com.anil.pfm.mf.service.impl;

import com.anil.pfm.mf.domain.MFPortfolio;
import com.anil.pfm.mf.repository.MFPortfolioRepository;
import com.anil.pfm.mf.service.MFPortfolioService;
import com.anil.pfm.mf.service.mapper.MFPortfolioMapper;
import com.anil.pfm.service.dto.MFPortfolioDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MFPortfolio.
 */
@Service
@Transactional
public class MFPortfolioServiceImpl implements MFPortfolioService{

    private final Logger log = LoggerFactory.getLogger(MFPortfolioServiceImpl.class);

    private final MFPortfolioRepository mFPortfolioRepository;

    private final MFPortfolioMapper mFPortfolioMapper;

    public MFPortfolioServiceImpl(MFPortfolioRepository mFPortfolioRepository, MFPortfolioMapper mFPortfolioMapper) {
        this.mFPortfolioRepository = mFPortfolioRepository;
        this.mFPortfolioMapper = mFPortfolioMapper;
    }

    /**
     * Save a mFPortfolio.
     *
     * @param mFPortfolioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MFPortfolioDTO save(MFPortfolioDTO mFPortfolioDTO) {
        log.debug("Request to save MFPortfolio : {}", mFPortfolioDTO);
        MFPortfolio mFPortfolio = mFPortfolioMapper.toEntity(mFPortfolioDTO);
        mFPortfolio = mFPortfolioRepository.save(mFPortfolio);
        return mFPortfolioMapper.toDto(mFPortfolio);
    }

    /**
     *  Get all the mFPortfolios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MFPortfolioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MFPortfolios");
        return mFPortfolioRepository.findAll(pageable)
            .map(mFPortfolioMapper::toDto);
    }

    /**
     *  Get one mFPortfolio by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MFPortfolioDTO findOne(Long id) {
        log.debug("Request to get MFPortfolio : {}", id);
        MFPortfolio mFPortfolio = mFPortfolioRepository.findOne(id);
        return mFPortfolioMapper.toDto(mFPortfolio);
    }

    /**
     *  Delete the  mFPortfolio by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MFPortfolio : {}", id);
        mFPortfolioRepository.delete(id);
    }
}
