package com.anil.pfm.repository;

import com.anil.pfm.domain.PPFTransaction;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import com.anil.pfm.tx.domain.Transaction;
import java.util.List;


/**
 * Spring Data JPA repository for the PPFTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PPFTransactionRepository extends JpaRepository<PPFTransaction, Long> {
	
	List<PPFTransaction> findByTransaction_Id(Long transactionId);

}
