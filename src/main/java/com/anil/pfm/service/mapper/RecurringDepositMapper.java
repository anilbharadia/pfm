package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.RecurringDepositDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RecurringDeposit and its DTO RecurringDepositDTO.
 */
@Mapper(componentModel = "spring", uses = {BankMapper.class, GoalMapper.class, PersonMapper.class})
public interface RecurringDepositMapper extends EntityMapper<RecurringDepositDTO, RecurringDeposit> {

    @Mapping(source = "bank.id", target = "bankId")
    @Mapping(source = "goal.id", target = "goalId")
    @Mapping(source = "owner.id", target = "ownerId")
    RecurringDepositDTO toDto(RecurringDeposit recurringDeposit); 

    @Mapping(source = "bankId", target = "bank")
    @Mapping(source = "goalId", target = "goal")
    @Mapping(source = "ownerId", target = "owner")
    RecurringDeposit toEntity(RecurringDepositDTO recurringDepositDTO);

    default RecurringDeposit fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecurringDeposit recurringDeposit = new RecurringDeposit();
        recurringDeposit.setId(id);
        return recurringDeposit;
    }
}
