package com.anil.pfm.repository;

import com.anil.pfm.domain.PPFAccount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PPFAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PPFAccountRepository extends JpaRepository<PPFAccount, Long> {

}
