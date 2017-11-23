package com.anil.pfm.mf.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.anil.pfm.mf.domain.AMC;
import com.anil.pfm.mf.repository.AMCRepository;
import com.anil.pfm.mf.service.dto.AMCDTO;
import com.anil.pfm.service.mapper.EntityMapper;

/**
 * Mapper for the entity AMC and its DTO AMCDTO.
 */
@Mapper(componentModel = "spring", uses = {MFRTAgentMapper.class})
public abstract class AMCMapper implements EntityMapper<AMCDTO, AMC> {

	@Autowired
	protected AMCRepository amcRepository;
	
    @Mapping(source = "mfrtAgent.id", target = "mfrtAgentId")
    public abstract AMCDTO toDto(AMC amc); 

    @Mapping(source = "mfrtAgentId", target = "mfrtAgent")
    public abstract AMC toEntity(AMCDTO amcDTO);

    public AMC fromId(Long id) {
        if (id == null) {
            return null;
        }
        return amcRepository.getOne(id);
    }
}
