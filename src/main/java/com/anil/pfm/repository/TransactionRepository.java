package com.anil.pfm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.anil.pfm.tx.domain.QTransaction;
import com.anil.pfm.tx.domain.Transaction;
import com.anil.pfm.tx.service.dto.FilterTransactionVM;
import com.querydsl.core.types.dsl.BooleanExpression;


/**
 * Spring Data JPA repository for the Transaction entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, QueryDslPredicateExecutor<Transaction> {

	default Page<Transaction> filter(FilterTransactionVM vm, Pageable pageable) {
		
		QTransaction t = QTransaction.transaction;
		BooleanExpression q = t.isNotNull();

		if (!CollectionUtils.isEmpty(vm.getTxTypeIds())) {
			q = q.and(t.txType.id.in(vm.getTxTypeIds()));
		}
		
		if (vm.getDateFrom() != null) {
			q = q.and(t.date.after(vm.getDateFrom()));
		}
		
		if (vm.getDateTo() != null) {
			q = q.and(t.date.before(vm.getDateTo()));
		}
		
		return findAll(q, pageable);
	}

}
