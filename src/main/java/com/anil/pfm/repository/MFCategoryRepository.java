package com.anil.pfm.repository;

import com.anil.pfm.domain.MFCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MFCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MFCategoryRepository extends JpaRepository<MFCategory, Long> {

}
