package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.repository.MyAccountRepository;
import com.anil.pfm.service.dto.MyAccountDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity MyAccount and its DTO MyAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {
	BankAccountMapper.class, 
	PersonMapper.class,
})
public abstract class MyAccountMapper implements EntityMapper<MyAccountDTO, MyAccount> {

	@Autowired
	protected MyAccountRepository myAccountRepository;
	
    @Mapping(source = "bankAccount.id", target = "bankAccountId")
    @Mapping(source = "owner.id", target = "ownerId")
	public abstract MyAccountDTO toDto(MyAccount myAccount); 

    @Mapping(source = "bankAccountId", target = "bankAccount")
    @Mapping(source = "ownerId", target = "owner")
    public abstract MyAccount toEntity(MyAccountDTO myAccountDTO);

    public MyAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        return myAccountRepository.findOne(id);
    }
}
