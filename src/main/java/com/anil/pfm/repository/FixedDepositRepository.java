package com.anil.pfm.repository;

import com.anil.pfm.domain.FixedDeposit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FixedDeposit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FixedDepositRepository extends JpaRepository<FixedDeposit, Long> {

}
