package com.anil.pfm.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anil.pfm.domain.PPFAccount;
import com.anil.pfm.domain.PPFTransaction;
import com.anil.pfm.repository.PPFTransactionRepository;
import com.anil.pfm.service.PPFTransactionService;
import com.anil.pfm.service.dto.PPFTransactionDTO;
import com.anil.pfm.service.mapper.PPFTransactionMapper;


/**
 * Service Implementation for managing PPFTransaction.
 */
@Service
@Transactional
public class PPFTransactionServiceImpl implements PPFTransactionService{

    private final Logger log = LoggerFactory.getLogger(PPFTransactionServiceImpl.class);

    private final PPFTransactionRepository txRepository;

    private final PPFTransactionMapper txMapper;

    public PPFTransactionServiceImpl(PPFTransactionRepository pPFTransactionRepository, PPFTransactionMapper pPFTransactionMapper) {
        this.txRepository = pPFTransactionRepository;
        this.txMapper = pPFTransactionMapper;
    }

    /**
     * Save a pPFTransaction.
     *
     * @param pPFTransactionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PPFTransactionDTO save(PPFTransactionDTO pPFTransactionDTO) {
        log.debug("Request to save PPFTransaction : {}", pPFTransactionDTO);
        PPFTransaction tx = txMapper.toEntity(pPFTransactionDTO);
        
        updateBalance(tx);
        
        tx = txRepository.save(tx);
        return txMapper.toDto(tx);
    }
    
    private void updateBalance(PPFTransaction tx) {

		PPFAccount account = tx.getAccount();
		
		BigDecimal balance = account.getBalance();

		balance = balance.add(tx.getAmount());
		
		account.setBalance(balance);
	}

    /**
     *  Get all the pPFTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PPFTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PPFTransactions");
        return txRepository.findAll(pageable)
            .map(txMapper::toDto);
    }

    /**
     *  Get one pPFTransaction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PPFTransactionDTO findOne(Long id) {
        log.debug("Request to get PPFTransaction : {}", id);
        PPFTransaction pPFTransaction = txRepository.findOne(id);
        return txMapper.toDto(pPFTransaction);
    }

    /**
     *  Delete the  pPFTransaction by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PPFTransaction : {}", id);
        
        PPFTransaction tx = txRepository.findOne(id);
		
		tx.setAmount(tx.getAmount().negate());
		updateBalance(tx);
        
        txRepository.delete(id);
    }
}
