package com.anil.pfm.mf.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.mf.domain.AMC;
import com.anil.pfm.service.dto.AMCDTO;
import com.anil.pfm.service.mapper.EntityMapper;

import org.mapstruct.*;

/**
 * Mapper for the entity AMC and its DTO AMCDTO.
 */
@Mapper(componentModel = "spring", uses = {MFRTAgentMapper.class})
public interface AMCMapper extends EntityMapper<AMCDTO, AMC> {

    @Mapping(source = "mfrtAgent.id", target = "mfrtAgentId")
    AMCDTO toDto(AMC aMC); 

    @Mapping(source = "mfrtAgentId", target = "mfrtAgent")
    AMC toEntity(AMCDTO aMCDTO);

    default AMC fromId(Long id) {
        if (id == null) {
            return null;
        }
        AMC aMC = new AMC();
        aMC.setId(id);
        return aMC;
    }
}
