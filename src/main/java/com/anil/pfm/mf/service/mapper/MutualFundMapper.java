package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.MutualFundDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MutualFund and its DTO MutualFundDTO.
 */
@Mapper(componentModel = "spring", uses = {AMCMapper.class, MFCategoryMapper.class})
public interface MutualFundMapper extends EntityMapper<MutualFundDTO, MutualFund> {

    @Mapping(source = "amc.id", target = "amcId")
    @Mapping(source = "category.id", target = "categoryId")
    MutualFundDTO toDto(MutualFund mutualFund); 

    @Mapping(source = "amcId", target = "amc")
    @Mapping(source = "categoryId", target = "category")
    MutualFund toEntity(MutualFundDTO mutualFundDTO);

    default MutualFund fromId(Long id) {
        if (id == null) {
            return null;
        }
        MutualFund mutualFund = new MutualFund();
        mutualFund.setId(id);
        return mutualFund;
    }
}
