package com.anil.pfm.repository;

import com.anil.pfm.domain.LifeInsurancePolicy;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LifeInsurancePolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LifeInsurancePolicyRepository extends JpaRepository<LifeInsurancePolicy, Long> {

}
