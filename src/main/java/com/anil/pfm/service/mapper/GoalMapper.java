package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.GoalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Goal and its DTO GoalDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoalMapper extends EntityMapper<GoalDTO, Goal> {

    

    

    default Goal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Goal goal = new Goal();
        goal.setId(id);
        return goal;
    }
}
