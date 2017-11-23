package com.anil.pfm.mf.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.anil.pfm.mf.domain.MFRTAgent;
import com.anil.pfm.mf.repository.MFRTAgentRepository;
import com.anil.pfm.mf.service.dto.MFRTAgentDTO;
import com.anil.pfm.service.mapper.EntityMapper;

/**
 * Mapper for the entity MFRTAgent and its DTO MFRTAgentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public abstract class MFRTAgentMapper implements EntityMapper<MFRTAgentDTO, MFRTAgent> {

	@Autowired
	protected MFRTAgentRepository mfrtAgentRepository;

	public MFRTAgent fromId(Long id) {
		if (id == null) {
			return null;
		}
		return mfrtAgentRepository.findOne(id);
	}
}
