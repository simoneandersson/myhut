package com.accenture.myhut.repository;

import com.accenture.myhut.domain.Cabin;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cabin entity.
 */
@SuppressWarnings("unused")
public interface CabinRepository extends JpaRepository<Cabin,Long> {

}
