package com.accenture.myhut.repository;

import com.accenture.myhut.domain.Booking;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Booking entity.
 */
@SuppressWarnings("unused")
public interface BookingRepository extends JpaRepository<Booking,Long> {

}
