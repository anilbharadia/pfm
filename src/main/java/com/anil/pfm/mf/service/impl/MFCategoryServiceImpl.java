package com.anil.pfm.mf.service.impl;

import com.anil.pfm.mf.domain.MFCategory;
import com.anil.pfm.mf.repository.MFCategoryRepository;
import com.anil.pfm.mf.service.MFCategoryService;
import com.anil.pfm.mf.service.dto.MFCategoryDTO;
import com.anil.pfm.mf.service.mapper.MFCategoryMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MFCategory.
 */
@Service
@Transactional
public class MFCategoryServiceImpl implements MFCategoryService{

    private final Logger log = LoggerFactory.getLogger(MFCategoryServiceImpl.class);

    private final MFCategoryRepository mFCategoryRepository;

    private final MFCategoryMapper mFCategoryMapper;

    public MFCategoryServiceImpl(MFCategoryRepository mFCategoryRepository, MFCategoryMapper mFCategoryMapper) {
        this.mFCategoryRepository = mFCategoryRepository;
        this.mFCategoryMapper = mFCategoryMapper;
    }

    /**
     * Save a mFCategory.
     *
     * @param mFCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MFCategoryDTO save(MFCategoryDTO mFCategoryDTO) {
        log.debug("Request to save MFCategory : {}", mFCategoryDTO);
        MFCategory mFCategory = mFCategoryMapper.toEntity(mFCategoryDTO);
        mFCategory = mFCategoryRepository.save(mFCategory);
        return mFCategoryMapper.toDto(mFCategory);
    }

    /**
     *  Get all the mFCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MFCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MFCategories");
        return mFCategoryRepository.findAll(pageable)
            .map(mFCategoryMapper::toDto);
    }

    /**
     *  Get one mFCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MFCategoryDTO findOne(Long id) {
        log.debug("Request to get MFCategory : {}", id);
        MFCategory mFCategory = mFCategoryRepository.findOne(id);
        return mFCategoryMapper.toDto(mFCategory);
    }

    /**
     *  Delete the  mFCategory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MFCategory : {}", id);
        mFCategoryRepository.delete(id);
    }
}
