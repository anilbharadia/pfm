package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.FixedDepositDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FixedDeposit and its DTO FixedDepositDTO.
 */
@Mapper(componentModel = "spring", uses = {BankMapper.class, GoalMapper.class, PersonMapper.class})
public interface FixedDepositMapper extends EntityMapper<FixedDepositDTO, FixedDeposit> {

    @Mapping(source = "bank.id", target = "bankId")
    @Mapping(source = "goal.id", target = "goalId")
    @Mapping(source = "owner.id", target = "ownerId")
    FixedDepositDTO toDto(FixedDeposit fixedDeposit); 

    @Mapping(source = "bankId", target = "bank")
    @Mapping(source = "goalId", target = "goal")
    @Mapping(source = "ownerId", target = "owner")
    FixedDeposit toEntity(FixedDepositDTO fixedDepositDTO);

    default FixedDeposit fromId(Long id) {
        if (id == null) {
            return null;
        }
        FixedDeposit fixedDeposit = new FixedDeposit();
        fixedDeposit.setId(id);
        return fixedDeposit;
    }
}
