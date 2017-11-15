package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.LifeInsuranceCompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LifeInsuranceCompany and its DTO LifeInsuranceCompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LifeInsuranceCompanyMapper extends EntityMapper<LifeInsuranceCompanyDTO, LifeInsuranceCompany> {

    

    

    default LifeInsuranceCompany fromId(Long id) {
        if (id == null) {
            return null;
        }
        LifeInsuranceCompany lifeInsuranceCompany = new LifeInsuranceCompany();
        lifeInsuranceCompany.setId(id);
        return lifeInsuranceCompany;
    }
}
