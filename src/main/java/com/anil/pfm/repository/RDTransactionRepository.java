package com.anil.pfm.repository;

import com.anil.pfm.domain.RDTransaction;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RDTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RDTransactionRepository extends JpaRepository<RDTransaction, Long> {

}
