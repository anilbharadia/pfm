package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.repository.FixedDepositRepository;
import com.anil.pfm.service.dto.FixedDepositDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity FixedDeposit and its DTO FixedDepositDTO.
 */
@Mapper(componentModel = "spring", uses = {BankMapper.class, GoalMapper.class, PersonMapper.class})
public abstract class FixedDepositMapper implements EntityMapper<FixedDepositDTO, FixedDeposit> {

	@Autowired
	protected FixedDepositRepository repository;
	
    @Mapping(source = "bank.id", target = "bankId")
    @Mapping(source = "goal.id", target = "goalId")
    @Mapping(source = "owner.id", target = "ownerId")
    public abstract FixedDepositDTO toDto(FixedDeposit fixedDeposit); 

    @Mapping(source = "bankId", target = "bank")
    @Mapping(source = "goalId", target = "goal")
    @Mapping(source = "ownerId", target = "owner")
    public abstract FixedDeposit toEntity(FixedDepositDTO fixedDepositDTO);

    public FixedDeposit fromId(Long id) {
        if (id == null) {
            return null;
        }
        return repository.findOne(id);
    }
}
