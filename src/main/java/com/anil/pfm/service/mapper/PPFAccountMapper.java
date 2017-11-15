package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.PPFAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PPFAccount and its DTO PPFAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {BankMapper.class, PersonMapper.class, GoalMapper.class})
public interface PPFAccountMapper extends EntityMapper<PPFAccountDTO, PPFAccount> {

    @Mapping(source = "bank.id", target = "bankId")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "goal.id", target = "goalId")
    PPFAccountDTO toDto(PPFAccount pPFAccount); 

    @Mapping(source = "bankId", target = "bank")
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "goalId", target = "goal")
    PPFAccount toEntity(PPFAccountDTO pPFAccountDTO);

    default PPFAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        PPFAccount pPFAccount = new PPFAccount();
        pPFAccount.setId(id);
        return pPFAccount;
    }
}
