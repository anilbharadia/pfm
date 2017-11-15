package com.anil.pfm.service.impl;

import com.anil.pfm.service.LifeInsurancePolicyService;
import com.anil.pfm.domain.LifeInsurancePolicy;
import com.anil.pfm.repository.LifeInsurancePolicyRepository;
import com.anil.pfm.service.dto.LifeInsurancePolicyDTO;
import com.anil.pfm.service.mapper.LifeInsurancePolicyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing LifeInsurancePolicy.
 */
@Service
@Transactional
public class LifeInsurancePolicyServiceImpl implements LifeInsurancePolicyService{

    private final Logger log = LoggerFactory.getLogger(LifeInsurancePolicyServiceImpl.class);

    private final LifeInsurancePolicyRepository lifeInsurancePolicyRepository;

    private final LifeInsurancePolicyMapper lifeInsurancePolicyMapper;

    public LifeInsurancePolicyServiceImpl(LifeInsurancePolicyRepository lifeInsurancePolicyRepository, LifeInsurancePolicyMapper lifeInsurancePolicyMapper) {
        this.lifeInsurancePolicyRepository = lifeInsurancePolicyRepository;
        this.lifeInsurancePolicyMapper = lifeInsurancePolicyMapper;
    }

    /**
     * Save a lifeInsurancePolicy.
     *
     * @param lifeInsurancePolicyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LifeInsurancePolicyDTO save(LifeInsurancePolicyDTO lifeInsurancePolicyDTO) {
        log.debug("Request to save LifeInsurancePolicy : {}", lifeInsurancePolicyDTO);
        LifeInsurancePolicy lifeInsurancePolicy = lifeInsurancePolicyMapper.toEntity(lifeInsurancePolicyDTO);
        lifeInsurancePolicy = lifeInsurancePolicyRepository.save(lifeInsurancePolicy);
        return lifeInsurancePolicyMapper.toDto(lifeInsurancePolicy);
    }

    /**
     *  Get all the lifeInsurancePolicies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LifeInsurancePolicyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LifeInsurancePolicies");
        return lifeInsurancePolicyRepository.findAll(pageable)
            .map(lifeInsurancePolicyMapper::toDto);
    }

    /**
     *  Get one lifeInsurancePolicy by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LifeInsurancePolicyDTO findOne(Long id) {
        log.debug("Request to get LifeInsurancePolicy : {}", id);
        LifeInsurancePolicy lifeInsurancePolicy = lifeInsurancePolicyRepository.findOne(id);
        return lifeInsurancePolicyMapper.toDto(lifeInsurancePolicy);
    }

    /**
     *  Delete the  lifeInsurancePolicy by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LifeInsurancePolicy : {}", id);
        lifeInsurancePolicyRepository.delete(id);
    }
}
