package com.anil.pfm.mf.service.impl;

import com.anil.pfm.domain.Goal;
import com.anil.pfm.mf.domain.MFInvestment;
import com.anil.pfm.mf.repository.MFInvestmentRepository;
import com.anil.pfm.mf.service.MFInvestmentService;
import com.anil.pfm.mf.service.dto.CreateMFInvestmentVM;
import com.anil.pfm.mf.service.dto.MFInvestmentDTO;
import com.anil.pfm.mf.service.dto.UpdateMFInvestmentVM;
import com.anil.pfm.mf.service.mapper.MFInvestmentMapper;
import com.anil.pfm.repository.TransactionRepository;
import com.anil.pfm.tx.domain.Transaction;
import com.anil.pfm.tx.service.TransactionService;
import com.anil.pfm.tx.service.dto.TransactionDTO;

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
public class MFInvestmentServiceImpl implements MFInvestmentService {

	private final Logger log = LoggerFactory.getLogger(MFInvestmentServiceImpl.class);

	private final MFInvestmentRepository mfInvestmentRepository;

	private final MFInvestmentMapper mfInvestmentMapper;

	private final TransactionService transactionService;

	private final TransactionRepository transactionRepository;

	public MFInvestmentServiceImpl(MFInvestmentRepository mFInvestmentRepository,
			MFInvestmentMapper mFInvestmentMapper, TransactionService transactionService, TransactionRepository transactionRepository) {
		this.mfInvestmentRepository = mFInvestmentRepository;
		this.mfInvestmentMapper = mFInvestmentMapper;
		this.transactionRepository = transactionRepository;
		this.transactionService = transactionService;
	}

	/**
	 * Save a mFInvestment.
	 *
	 * @param vm
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public MFInvestmentDTO save(CreateMFInvestmentVM vm) {
		log.debug("Request to save MFInvestment : {}", vm);
		MFInvestment mfInvestment = mfInvestmentMapper.toEntity(vm);

		updateBalance(mfInvestment);

		mfInvestment = mfInvestmentRepository.save(mfInvestment);
		return mfInvestmentMapper.toDto(mfInvestment);
	}
	
	@Override
	public MFInvestmentDTO update(UpdateMFInvestmentVM vm) {
		log.debug("Request to update MFInvestment : {}", vm);
		
		MFInvestment mfInvestment = mfInvestmentRepository.findOne(vm.getId());
		
		mfInvestmentMapper.update(mfInvestment, vm);
		
		mfInvestment = mfInvestmentRepository.save(mfInvestment);
		
		return mfInvestmentMapper.toDto(mfInvestment);
	}

	private void updateBalance(MFInvestment mfInvestment) {
		updateGoalBalance(mfInvestment);
		updateMyAccountBalance(mfInvestment);
	}

	private void updateMyAccountBalance(MFInvestment mfInvestment) {
		TransactionDTO tx = transactionService
				.save(mfInvestmentMapper.toCreateTransactionVM(mfInvestment));

		mfInvestment.setTransaction(transactionRepository.findOne(tx.getId()));
	}

	private void updateGoalBalance(MFInvestment mfInvestment) {
		Goal goal = mfInvestment.getGoal();
		
		if (goal == null) {
			log.warn("Update Goal Balance : There is no goal attached to MF Investment[id={}]", mfInvestment.getId());
			return;
		}
		
		goal.setBalance(goal.getBalance().add(mfInvestment.getAmount()));
	}

	/**
	 * Get all the mFInvestments.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<MFInvestmentDTO> findAll(Pageable pageable) {
		log.debug("Request to get all MFInvestments");
		return mfInvestmentRepository.findAll(pageable).map(mfInvestmentMapper::toDto);
	}

	/**
	 * Get one mFInvestment by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public MFInvestmentDTO findOne(Long id) {
		log.debug("Request to get MFInvestment : {}", id);
		MFInvestment mFInvestment = mfInvestmentRepository.findOne(id);
		return mfInvestmentMapper.toDto(mFInvestment);
	}

	/**
	 * Delete the mFInvestment by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete MFInvestment : {}", id);
		
		MFInvestment mfInvestment = mfInvestmentRepository.findOne(id);
		mfInvestment.setAmount(mfInvestment.getAmount().negate());
		updateGoalBalance(mfInvestment);

		Transaction transaction = mfInvestment.getTransaction();
		
		mfInvestmentRepository.delete(id);

		if (transaction != null) {
			transactionService.delete(transaction.getId());
		}
	}

}
