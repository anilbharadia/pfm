package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.MyAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MyAccount and its DTO MyAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {BankAccountMapper.class, PersonMapper.class})
public interface MyAccountMapper extends EntityMapper<MyAccountDTO, MyAccount> {

    @Mapping(source = "bankAccount.id", target = "bankAccountId")
    @Mapping(source = "owner.id", target = "ownerId")
    MyAccountDTO toDto(MyAccount myAccount); 

    @Mapping(source = "bankAccountId", target = "bankAccount")
    @Mapping(source = "ownerId", target = "owner")
    MyAccount toEntity(MyAccountDTO myAccountDTO);

    default MyAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        MyAccount myAccount = new MyAccount();
        myAccount.setId(id);
        return myAccount;
    }
}
