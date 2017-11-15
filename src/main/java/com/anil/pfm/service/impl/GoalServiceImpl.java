package com.anil.pfm.service.impl;

import com.anil.pfm.service.GoalService;
import com.anil.pfm.domain.Goal;
import com.anil.pfm.repository.GoalRepository;
import com.anil.pfm.service.dto.GoalDTO;
import com.anil.pfm.service.mapper.GoalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Goal.
 */
@Service
@Transactional
public class GoalServiceImpl implements GoalService{

    private final Logger log = LoggerFactory.getLogger(GoalServiceImpl.class);

    private final GoalRepository goalRepository;

    private final GoalMapper goalMapper;

    public GoalServiceImpl(GoalRepository goalRepository, GoalMapper goalMapper) {
        this.goalRepository = goalRepository;
        this.goalMapper = goalMapper;
    }

    /**
     * Save a goal.
     *
     * @param goalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GoalDTO save(GoalDTO goalDTO) {
        log.debug("Request to save Goal : {}", goalDTO);
        Goal goal = goalMapper.toEntity(goalDTO);
        goal = goalRepository.save(goal);
        return goalMapper.toDto(goal);
    }

    /**
     *  Get all the goals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GoalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Goals");
        return goalRepository.findAll(pageable)
            .map(goalMapper::toDto);
    }

    /**
     *  Get one goal by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GoalDTO findOne(Long id) {
        log.debug("Request to get Goal : {}", id);
        Goal goal = goalRepository.findOne(id);
        return goalMapper.toDto(goal);
    }

    /**
     *  Delete the  goal by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Goal : {}", id);
        goalRepository.delete(id);
    }
}
