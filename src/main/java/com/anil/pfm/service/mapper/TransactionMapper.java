package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.CreateTransactionVM;
import com.anil.pfm.service.dto.TransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Transaction and its DTO TransactionDTO.
 */
@Mapper(componentModel = "spring", uses = {
		MyAccountMapper.class, 
		TransactionTypeMapper.class, 
		ExpenseCategoryMapper.class, 
		IncomeCategoryMapper.class})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "txType.id", target = "txTypeId")
    @Mapping(source = "expenseCategory.id", target = "expenseCategoryId")
    @Mapping(source = "incomeCategory.id", target = "incomeCategoryId")
    TransactionDTO toDto(Transaction transaction); 

    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "txTypeId", target = "txType")
    @Mapping(source = "expenseCategoryId", target = "expenseCategory")
    @Mapping(source = "incomeCategoryId", target = "incomeCategory")
    Transaction toEntity(TransactionDTO transactionDTO);
    
    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "txTypeId", target = "txType")
    @Mapping(source = "expenseCategoryId", target = "expenseCategory")
    @Mapping(source = "incomeCategoryId", target = "incomeCategory")
    Transaction toEntity(CreateTransactionVM vm);

    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
