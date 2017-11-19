package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.repository.RecurringDepositRepository;
import com.anil.pfm.service.dto.RecurringDepositDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity RecurringDeposit and its DTO RecurringDepositDTO.
 */
@Mapper(componentModel = "spring", uses = {BankMapper.class, GoalMapper.class, PersonMapper.class})
public abstract class RecurringDepositMapper implements EntityMapper<RecurringDepositDTO, RecurringDeposit> {

	@Autowired
	protected RecurringDepositRepository repository;
	
    @Mapping(source = "bank.id", target = "bankId")
    @Mapping(source = "goal.id", target = "goalId")
    @Mapping(source = "owner.id", target = "ownerId")
    public abstract RecurringDepositDTO toDto(RecurringDeposit recurringDeposit); 

    @Mapping(source = "bankId", target = "bank")
    @Mapping(source = "goalId", target = "goal")
    @Mapping(source = "ownerId", target = "owner")
    public abstract RecurringDeposit toEntity(RecurringDepositDTO recurringDepositDTO);

    public RecurringDeposit fromId(Long id) {
        if (id == null) {
            return null;
        }
        return repository.findOne(id);
    }
}
