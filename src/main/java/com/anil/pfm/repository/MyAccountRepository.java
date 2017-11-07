package com.anil.pfm.repository;

import com.anil.pfm.domain.MyAccount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MyAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyAccountRepository extends JpaRepository<MyAccount, Long> {

}
