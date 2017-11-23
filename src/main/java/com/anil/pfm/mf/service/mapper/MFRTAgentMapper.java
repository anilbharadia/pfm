package com.anil.pfm.mf.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.mf.domain.MFRTAgent;
import com.anil.pfm.service.dto.MFRTAgentDTO;
import com.anil.pfm.service.mapper.EntityMapper;

import org.mapstruct.*;

/**
 * Mapper for the entity MFRTAgent and its DTO MFRTAgentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MFRTAgentMapper extends EntityMapper<MFRTAgentDTO, MFRTAgent> {

    

    

    default MFRTAgent fromId(Long id) {
        if (id == null) {
            return null;
        }
        MFRTAgent mFRTAgent = new MFRTAgent();
        mFRTAgent.setId(id);
        return mFRTAgent;
    }
}
