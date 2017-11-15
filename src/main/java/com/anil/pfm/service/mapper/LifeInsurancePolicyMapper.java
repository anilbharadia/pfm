package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.LifeInsurancePolicyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LifeInsurancePolicy and its DTO LifeInsurancePolicyDTO.
 */
@Mapper(componentModel = "spring", uses = {LifeInsuranceCompanyMapper.class, PersonMapper.class, GoalMapper.class})
public interface LifeInsurancePolicyMapper extends EntityMapper<LifeInsurancePolicyDTO, LifeInsurancePolicy> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "goal.id", target = "goalId")
    LifeInsurancePolicyDTO toDto(LifeInsurancePolicy lifeInsurancePolicy); 

    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "goalId", target = "goal")
    LifeInsurancePolicy toEntity(LifeInsurancePolicyDTO lifeInsurancePolicyDTO);

    default LifeInsurancePolicy fromId(Long id) {
        if (id == null) {
            return null;
        }
        LifeInsurancePolicy lifeInsurancePolicy = new LifeInsurancePolicy();
        lifeInsurancePolicy.setId(id);
        return lifeInsurancePolicy;
    }
}
