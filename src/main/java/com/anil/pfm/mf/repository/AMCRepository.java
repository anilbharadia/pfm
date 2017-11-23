package com.anil.pfm.mf.repository;

import org.springframework.stereotype.Repository;

import com.anil.pfm.mf.domain.AMC;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AMC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AMCRepository extends JpaRepository<AMC, Long> {

}
