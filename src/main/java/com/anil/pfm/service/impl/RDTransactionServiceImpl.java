package com.anil.pfm.service.impl;

import com.anil.pfm.service.RDTransactionService;
import com.anil.pfm.domain.RDTransaction;
import com.anil.pfm.repository.RDTransactionRepository;
import com.anil.pfm.service.dto.RDTransactionDTO;
import com.anil.pfm.service.mapper.RDTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RDTransaction.
 */
@Service
@Transactional
public class RDTransactionServiceImpl implements RDTransactionService{

    private final Logger log = LoggerFactory.getLogger(RDTransactionServiceImpl.class);

    private final RDTransactionRepository rDTransactionRepository;

    private final RDTransactionMapper rDTransactionMapper;

    public RDTransactionServiceImpl(RDTransactionRepository rDTransactionRepository, RDTransactionMapper rDTransactionMapper) {
        this.rDTransactionRepository = rDTransactionRepository;
        this.rDTransactionMapper = rDTransactionMapper;
    }

    /**
     * Save a rDTransaction.
     *
     * @param rDTransactionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RDTransactionDTO save(RDTransactionDTO rDTransactionDTO) {
        log.debug("Request to save RDTransaction : {}", rDTransactionDTO);
        RDTransaction rDTransaction = rDTransactionMapper.toEntity(rDTransactionDTO);
        rDTransaction = rDTransactionRepository.save(rDTransaction);
        return rDTransactionMapper.toDto(rDTransaction);
    }

    /**
     *  Get all the rDTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RDTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RDTransactions");
        return rDTransactionRepository.findAll(pageable)
            .map(rDTransactionMapper::toDto);
    }

    /**
     *  Get one rDTransaction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RDTransactionDTO findOne(Long id) {
        log.debug("Request to get RDTransaction : {}", id);
        RDTransaction rDTransaction = rDTransactionRepository.findOne(id);
        return rDTransactionMapper.toDto(rDTransaction);
    }

    /**
     *  Delete the  rDTransaction by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RDTransaction : {}", id);
        rDTransactionRepository.delete(id);
    }
}
