package com.anil.pfm.repository;

import com.anil.pfm.domain.MFInvestment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MFInvestment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MFInvestmentRepository extends JpaRepository<MFInvestment, Long> {

}
