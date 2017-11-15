package com.anil.pfm.repository;

import com.anil.pfm.domain.RecurringDeposit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RecurringDeposit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecurringDepositRepository extends JpaRepository<RecurringDeposit, Long> {

}
