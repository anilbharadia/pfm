package com.anil.pfm.repository;

import com.anil.pfm.domain.MFPortfolio;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MFPortfolio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MFPortfolioRepository extends JpaRepository<MFPortfolio, Long> {

}
