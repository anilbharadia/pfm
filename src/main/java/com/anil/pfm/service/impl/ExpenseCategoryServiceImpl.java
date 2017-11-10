package com.anil.pfm.service.impl;

import com.anil.pfm.service.ExpenseCategoryService;
import com.anil.pfm.domain.ExpenseCategory;
import com.anil.pfm.repository.ExpenseCategoryRepository;
import com.anil.pfm.service.dto.ExpenseCategoryDTO;
import com.anil.pfm.service.mapper.ExpenseCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ExpenseCategory.
 */
@Service
@Transactional
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService{

    private final Logger log = LoggerFactory.getLogger(ExpenseCategoryServiceImpl.class);

    private final ExpenseCategoryRepository expenseCategoryRepository;

    private final ExpenseCategoryMapper expenseCategoryMapper;

    public ExpenseCategoryServiceImpl(ExpenseCategoryRepository expenseCategoryRepository, ExpenseCategoryMapper expenseCategoryMapper) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.expenseCategoryMapper = expenseCategoryMapper;
    }

    /**
     * Save a expenseCategory.
     *
     * @param expenseCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExpenseCategoryDTO save(ExpenseCategoryDTO expenseCategoryDTO) {
        log.debug("Request to save ExpenseCategory : {}", expenseCategoryDTO);
        ExpenseCategory expenseCategory = expenseCategoryMapper.toEntity(expenseCategoryDTO);
        expenseCategory = expenseCategoryRepository.save(expenseCategory);
        return expenseCategoryMapper.toDto(expenseCategory);
    }

    /**
     *  Get all the expenseCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExpenseCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExpenseCategories");
        return expenseCategoryRepository.findAll(pageable)
            .map(expenseCategoryMapper::toDto);
    }

    /**
     *  Get one expenseCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExpenseCategoryDTO findOne(Long id) {
        log.debug("Request to get ExpenseCategory : {}", id);
        ExpenseCategory expenseCategory = expenseCategoryRepository.findOne(id);
        return expenseCategoryMapper.toDto(expenseCategory);
    }

    /**
     *  Delete the  expenseCategory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExpenseCategory : {}", id);
        expenseCategoryRepository.delete(id);
    }
}
