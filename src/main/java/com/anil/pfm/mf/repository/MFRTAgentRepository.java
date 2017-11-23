package com.anil.pfm.mf.repository;

import org.springframework.stereotype.Repository;

import com.anil.pfm.mf.domain.MFRTAgent;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MFRTAgent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MFRTAgentRepository extends JpaRepository<MFRTAgent, Long> {

}
