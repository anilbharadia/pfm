package com.anil.pfm.service.impl;

import com.anil.pfm.service.TransactionTypeService;
import com.anil.pfm.domain.TransactionType;
import com.anil.pfm.repository.TransactionTypeRepository;
import com.anil.pfm.service.dto.TransactionTypeDTO;
import com.anil.pfm.service.mapper.TransactionTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TransactionType.
 */
@Service
@Transactional
public class TransactionTypeServiceImpl implements TransactionTypeService{

    private final Logger log = LoggerFactory.getLogger(TransactionTypeServiceImpl.class);

    private final TransactionTypeRepository transactionTypeRepository;

    private final TransactionTypeMapper transactionTypeMapper;

    public TransactionTypeServiceImpl(TransactionTypeRepository transactionTypeRepository, TransactionTypeMapper transactionTypeMapper) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionTypeMapper = transactionTypeMapper;
    }

    /**
     * Save a transactionType.
     *
     * @param transactionTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TransactionTypeDTO save(TransactionTypeDTO transactionTypeDTO) {
        log.debug("Request to save TransactionType : {}", transactionTypeDTO);
        TransactionType transactionType = transactionTypeMapper.toEntity(transactionTypeDTO);
        transactionType = transactionTypeRepository.save(transactionType);
        return transactionTypeMapper.toDto(transactionType);
    }

    /**
     *  Get all the transactionTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransactionTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionTypes");
        return transactionTypeRepository.findAll(pageable)
            .map(transactionTypeMapper::toDto);
    }

    /**
     *  Get one transactionType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TransactionTypeDTO findOne(Long id) {
        log.debug("Request to get TransactionType : {}", id);
        TransactionType transactionType = transactionTypeRepository.findOne(id);
        return transactionTypeMapper.toDto(transactionType);
    }

    /**
     *  Delete the  transactionType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionType : {}", id);
        transactionTypeRepository.delete(id);
    }
}
