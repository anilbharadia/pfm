package com.anil.pfm.repository;

import com.anil.pfm.domain.ExpenseCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExpenseCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

}
