package com.accenture.myhut.service;

import com.accenture.myhut.domain.Booking;
import com.accenture.myhut.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Booking.
 */
@Service
@Transactional
public class BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingService.class);
    
    @Inject
    private BookingRepository bookingRepository;
    
    /**
     * Save a booking.
     * 
     * @param booking the entity to save
     * @return the persisted entity
     */
    public Booking save(Booking booking) {
        log.debug("Request to save Booking : {}", booking);
        Booking result = bookingRepository.save(booking);
        return result;
    }

    /**
     *  Get all the bookings.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Booking> findAll() {
        log.debug("Request to get all Bookings");
        List<Booking> result = bookingRepository.findAll();
        return result;
    }

    /**
     *  Get one booking by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Booking findOne(Long id) {
        log.debug("Request to get Booking : {}", id);
        Booking booking = bookingRepository.findOne(id);
        return booking;
    }

    /**
     *  Delete the  booking by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Booking : {}", id);
        bookingRepository.delete(id);
    }
}
