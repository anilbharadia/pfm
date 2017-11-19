package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.repository.PPFAccountRepository;
import com.anil.pfm.service.dto.PPFAccountDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity PPFAccount and its DTO PPFAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {BankMapper.class, PersonMapper.class, GoalMapper.class})
public abstract class PPFAccountMapper implements EntityMapper<PPFAccountDTO, PPFAccount> {

	@Autowired
	protected PPFAccountRepository repository;
	
    @Mapping(source = "bank.id", target = "bankId")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "goal.id", target = "goalId")
    public abstract PPFAccountDTO toDto(PPFAccount pPFAccount); 

    @Mapping(source = "bankId", target = "bank")
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "goalId", target = "goal")
    public abstract PPFAccount toEntity(PPFAccountDTO pPFAccountDTO);

    public PPFAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        return repository.findOne(id);
    }
}
