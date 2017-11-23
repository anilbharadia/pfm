package com.anil.pfm.repository;

import com.anil.pfm.domain.AMC;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AMC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AMCRepository extends JpaRepository<AMC, Long> {

}
