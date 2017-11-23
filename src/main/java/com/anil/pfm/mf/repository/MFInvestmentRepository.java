package com.anil.pfm.mf.repository;

import org.springframework.stereotype.Repository;

import com.anil.pfm.mf.domain.MFInvestment;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MFInvestment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MFInvestmentRepository extends JpaRepository<MFInvestment, Long> {

}
