package com.anil.pfm.service.impl;

import com.anil.pfm.service.IncomeCategoryService;
import com.anil.pfm.domain.IncomeCategory;
import com.anil.pfm.repository.IncomeCategoryRepository;
import com.anil.pfm.service.dto.IncomeCategoryDTO;
import com.anil.pfm.service.mapper.IncomeCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing IncomeCategory.
 */
@Service
@Transactional
public class IncomeCategoryServiceImpl implements IncomeCategoryService{

    private final Logger log = LoggerFactory.getLogger(IncomeCategoryServiceImpl.class);

    private final IncomeCategoryRepository incomeCategoryRepository;

    private final IncomeCategoryMapper incomeCategoryMapper;

    public IncomeCategoryServiceImpl(IncomeCategoryRepository incomeCategoryRepository, IncomeCategoryMapper incomeCategoryMapper) {
        this.incomeCategoryRepository = incomeCategoryRepository;
        this.incomeCategoryMapper = incomeCategoryMapper;
    }

    /**
     * Save a incomeCategory.
     *
     * @param incomeCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IncomeCategoryDTO save(IncomeCategoryDTO incomeCategoryDTO) {
        log.debug("Request to save IncomeCategory : {}", incomeCategoryDTO);
        IncomeCategory incomeCategory = incomeCategoryMapper.toEntity(incomeCategoryDTO);
        incomeCategory = incomeCategoryRepository.save(incomeCategory);
        return incomeCategoryMapper.toDto(incomeCategory);
    }

    /**
     *  Get all the incomeCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IncomeCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncomeCategories");
        return incomeCategoryRepository.findAll(pageable)
            .map(incomeCategoryMapper::toDto);
    }

    /**
     *  Get one incomeCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IncomeCategoryDTO findOne(Long id) {
        log.debug("Request to get IncomeCategory : {}", id);
        IncomeCategory incomeCategory = incomeCategoryRepository.findOne(id);
        return incomeCategoryMapper.toDto(incomeCategory);
    }

    /**
     *  Delete the  incomeCategory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IncomeCategory : {}", id);
        incomeCategoryRepository.delete(id);
    }
}
