package com.anil.pfm.mf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anil.pfm.mf.domain.MutualFund;


/**
 * Spring Data JPA repository for the MutualFund entity.
 */
@Repository
public interface MutualFundRepository extends JpaRepository<MutualFund, Long> {

	MutualFund findByNameAndAmc_Name(String name, String amc_name);
	
}
