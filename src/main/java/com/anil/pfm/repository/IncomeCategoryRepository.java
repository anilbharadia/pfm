package com.anil.pfm.repository;

import com.anil.pfm.domain.IncomeCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IncomeCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {

}
