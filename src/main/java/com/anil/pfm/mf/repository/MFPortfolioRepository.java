package com.anil.pfm.mf.repository;

import org.springframework.stereotype.Repository;

import com.anil.pfm.mf.domain.MFPortfolio;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MFPortfolio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MFPortfolioRepository extends JpaRepository<MFPortfolio, Long> {

}
