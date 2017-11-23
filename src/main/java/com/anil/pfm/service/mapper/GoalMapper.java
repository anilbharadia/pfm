package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.repository.GoalRepository;
import com.anil.pfm.service.dto.GoalDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity Goal and its DTO GoalDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public abstract class GoalMapper implements EntityMapper<GoalDTO, Goal> {

    @Autowired
	protected GoalRepository goalRepository;
    

    public Goal fromId(Long id) {
        if (id == null) {
            return null;
        }
        return goalRepository.findOne(id);
    }
}
