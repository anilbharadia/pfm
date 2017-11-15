package com.anil.pfm.repository;

import com.anil.pfm.domain.MutualFund;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MutualFund entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MutualFundRepository extends JpaRepository<MutualFund, Long> {

}
