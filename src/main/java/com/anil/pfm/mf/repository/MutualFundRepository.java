package com.anil.pfm.mf.repository;

import org.springframework.stereotype.Repository;

import com.anil.pfm.mf.domain.MutualFund;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MutualFund entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MutualFundRepository extends JpaRepository<MutualFund, Long> {

}
