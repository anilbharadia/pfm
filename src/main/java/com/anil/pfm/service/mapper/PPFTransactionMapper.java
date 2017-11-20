package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.repository.PPFTransactionRepository;
import com.anil.pfm.service.dto.PPFTransactionDTO;
import com.anil.pfm.tx.service.dto.CreateTransactionVM;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity PPFTransaction and its DTO PPFTransactionDTO.
 */
@Mapper(componentModel = "spring", uses = {PPFAccountMapper.class, MyAccountMapper.class})
public abstract class PPFTransactionMapper implements EntityMapper<PPFTransactionDTO, PPFTransaction> {

	@Autowired
	PPFTransactionRepository repository;
	
    @Mapping(source = "account.id", target = "accountId")
    public abstract PPFTransactionDTO toDto(PPFTransaction tx); 

    @Mapping(source = "accountId", target = "account")
    @Mapping(target="fromAccount", source="fromAccountId")
    public abstract PPFTransaction toEntity(PPFTransactionDTO tx);
    
    @Mapping(target="accountId", source="tx.fromAccount.id")
	@Mapping(target="txTypeId", expression="java(com.anil.pfm.domain.TransactionType.INVESTMENT)")
    public abstract CreateTransactionVM toCreateTransactionVM(PPFTransaction tx);

    public PPFTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        return repository.findOne(id);
    }
}
