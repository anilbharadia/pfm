package com.anil.pfm.repository;

import com.anil.pfm.domain.LifeInsuranceCompany;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LifeInsuranceCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LifeInsuranceCompanyRepository extends JpaRepository<LifeInsuranceCompany, Long> {

}
