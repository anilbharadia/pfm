package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.RDTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RDTransaction and its DTO RDTransactionDTO.
 */
@Mapper(componentModel = "spring", uses = {RecurringDepositMapper.class})
public interface RDTransactionMapper extends EntityMapper<RDTransactionDTO, RDTransaction> {

    @Mapping(source = "recurringDeposit.id", target = "recurringDepositId")
    RDTransactionDTO toDto(RDTransaction rDTransaction); 

    @Mapping(source = "recurringDepositId", target = "recurringDeposit")
    RDTransaction toEntity(RDTransactionDTO rDTransactionDTO);

    default RDTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        RDTransaction rDTransaction = new RDTransaction();
        rDTransaction.setId(id);
        return rDTransaction;
    }
}
