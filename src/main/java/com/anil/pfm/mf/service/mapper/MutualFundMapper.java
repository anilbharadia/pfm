package com.anil.pfm.mf.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.anil.pfm.mf.domain.MutualFund;
import com.anil.pfm.mf.repository.MutualFundRepository;
import com.anil.pfm.mf.service.dto.MutualFundDTO;
import com.anil.pfm.service.mapper.EntityMapper;

/**
 * Mapper for the entity MutualFund and its DTO MutualFundDTO.
 */
@Mapper(componentModel = "spring", uses = {AMCMapper.class, MFCategoryMapper.class})
public abstract class MutualFundMapper implements EntityMapper<MutualFundDTO, MutualFund> {

	@Autowired
	protected MutualFundRepository mutualFundRepository;
	
    @Mapping(source = "amc.id", target = "amcId")
    @Mapping(source = "category.id", target = "categoryId")
    public abstract MutualFundDTO toDto(MutualFund mutualFund); 

    @Mapping(source = "amcId", target = "amc")
    @Mapping(source = "categoryId", target = "category")
    public abstract MutualFund toEntity(MutualFundDTO mutualFundDTO);

    public MutualFund fromId(Long id) {
        if (id == null) {
            return null;
        }
        return mutualFundRepository.findOne(id);
    }
}
