package com.anil.pfm.mf.repository;

import org.springframework.stereotype.Repository;

import com.anil.pfm.mf.domain.MFCategory;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MFCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MFCategoryRepository extends JpaRepository<MFCategory, Long> {

}
