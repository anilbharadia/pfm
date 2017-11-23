package com.anil.pfm.repository;

import com.anil.pfm.domain.MFRTAgent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MFRTAgent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MFRTAgentRepository extends JpaRepository<MFRTAgent, Long> {

}
