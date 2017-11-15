package com.anil.pfm.service.impl;

import com.anil.pfm.service.LifeInsuranceCompanyService;
import com.anil.pfm.domain.LifeInsuranceCompany;
import com.anil.pfm.repository.LifeInsuranceCompanyRepository;
import com.anil.pfm.service.dto.LifeInsuranceCompanyDTO;
import com.anil.pfm.service.mapper.LifeInsuranceCompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing LifeInsuranceCompany.
 */
@Service
@Transactional
public class LifeInsuranceCompanyServiceImpl implements LifeInsuranceCompanyService{

    private final Logger log = LoggerFactory.getLogger(LifeInsuranceCompanyServiceImpl.class);

    private final LifeInsuranceCompanyRepository lifeInsuranceCompanyRepository;

    private final LifeInsuranceCompanyMapper lifeInsuranceCompanyMapper;

    public LifeInsuranceCompanyServiceImpl(LifeInsuranceCompanyRepository lifeInsuranceCompanyRepository, LifeInsuranceCompanyMapper lifeInsuranceCompanyMapper) {
        this.lifeInsuranceCompanyRepository = lifeInsuranceCompanyRepository;
        this.lifeInsuranceCompanyMapper = lifeInsuranceCompanyMapper;
    }

    /**
     * Save a lifeInsuranceCompany.
     *
     * @param lifeInsuranceCompanyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LifeInsuranceCompanyDTO save(LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO) {
        log.debug("Request to save LifeInsuranceCompany : {}", lifeInsuranceCompanyDTO);
        LifeInsuranceCompany lifeInsuranceCompany = lifeInsuranceCompanyMapper.toEntity(lifeInsuranceCompanyDTO);
        lifeInsuranceCompany = lifeInsuranceCompanyRepository.save(lifeInsuranceCompany);
        return lifeInsuranceCompanyMapper.toDto(lifeInsuranceCompany);
    }

    /**
     *  Get all the lifeInsuranceCompanies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LifeInsuranceCompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LifeInsuranceCompanies");
        return lifeInsuranceCompanyRepository.findAll(pageable)
            .map(lifeInsuranceCompanyMapper::toDto);
    }

    /**
     *  Get one lifeInsuranceCompany by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LifeInsuranceCompanyDTO findOne(Long id) {
        log.debug("Request to get LifeInsuranceCompany : {}", id);
        LifeInsuranceCompany lifeInsuranceCompany = lifeInsuranceCompanyRepository.findOne(id);
        return lifeInsuranceCompanyMapper.toDto(lifeInsuranceCompany);
    }

    /**
     *  Delete the  lifeInsuranceCompany by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LifeInsuranceCompany : {}", id);
        lifeInsuranceCompanyRepository.delete(id);
    }
}
