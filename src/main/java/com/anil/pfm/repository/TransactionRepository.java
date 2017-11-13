package com.anil.pfm.repository;

import com.anil.pfm.domain.QTransaction;
import com.anil.pfm.domain.Transaction;
import com.anil.pfm.tx.service.dto.FilterTransactionVM;
import com.anil.pfm.tx.service.dto.TransactionDTO;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;


/**
 * Spring Data JPA repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, QueryDslPredicateExecutor<Transaction> {

	default Page<Transaction> filter(FilterTransactionVM vm, Pageable pageable) {
		
		QTransaction t = QTransaction.transaction;
		BooleanExpression q = t.isNotNull();

		if (!CollectionUtils.isEmpty(vm.getTxTypeIds())) {
			q = q.and(t.txType.id.in(vm.getTxTypeIds()));
		}
		
		return findAll(q, pageable);
	}

}
