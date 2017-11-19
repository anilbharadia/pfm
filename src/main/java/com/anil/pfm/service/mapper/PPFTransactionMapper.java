package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.PPFTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PPFTransaction and its DTO PPFTransactionDTO.
 */
@Mapper(componentModel = "spring", uses = {PPFAccountMapper.class})
public interface PPFTransactionMapper extends EntityMapper<PPFTransactionDTO, PPFTransaction> {

    @Mapping(source = "account.id", target = "accountId")
    PPFTransactionDTO toDto(PPFTransaction tx); 

    @Mapping(source = "accountId", target = "account")
    PPFTransaction toEntity(PPFTransactionDTO pPFTransactionDTO);

    default PPFTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        PPFTransaction pPFTransaction = new PPFTransaction();
        pPFTransaction.setId(id);
        return pPFTransaction;
    }
}
