package com.anil.pfm.service.mapper;

import com.anil.pfm.tx.domain.Transaction;
import com.anil.pfm.tx.service.dto.CreateTransactionVM;
import com.anil.pfm.tx.service.dto.TransactionDTO;
import com.anil.pfm.tx.service.dto.UpdateTransactionVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
    Transaction update(UpdateTransactionVM vm, @MappingTarget Transaction tx);

    @Mapping(ignore = true, target="id")
    @Mapping(ignore = true, target="openingBalance")
    @Mapping(ignore = true, target="closingBalance")
    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "transferAccountId", target = "transferAccount")
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
