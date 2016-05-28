package com.accenture.myhut.web.rest;

import com.accenture.myhut.MyHutApp;
import com.accenture.myhut.domain.Booking;
import com.accenture.myhut.repository.BookingRepository;
import com.accenture.myhut.service.BookingService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.accenture.myhut.domain.enumeration.BookingType;
import com.accenture.myhut.domain.enumeration.BookingStatus;

/**
 * Test class for the BookingResource REST controller.
 *
 * @see BookingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyHutApp.class)
@WebAppConfiguration
@IntegrationTest
public class BookingResourceIntTest {


    private static final BookingType DEFAULT_BOOKING_TYPE = BookingType.PROJECT;
    private static final BookingType UPDATED_BOOKING_TYPE = BookingType.PRIVATE;

    private static final BookingStatus DEFAULT_BOOKING_STATUS = BookingStatus.CONFIRMED;
    private static final BookingStatus UPDATED_BOOKING_STATUS = BookingStatus.PENDING;
    private static final String DEFAULT_WBS = "AAAAA";
    private static final String UPDATED_WBS = "BBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private BookingRepository bookingRepository;

    @Inject
    private BookingService bookingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBookingMockMvc;

    private Booking booking;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookingResource bookingResource = new BookingResource();
        ReflectionTestUtils.setField(bookingResource, "bookingService", bookingService);
        this.restBookingMockMvc = MockMvcBuilders.standaloneSetup(bookingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        booking = new Booking();
        booking.setBookingType(DEFAULT_BOOKING_TYPE);
        booking.setBookingStatus(DEFAULT_BOOKING_STATUS);
        booking.setWbs(DEFAULT_WBS);
        booking.setStartDate(DEFAULT_START_DATE);
        booking.setEndDate(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createBooking() throws Exception {
        int databaseSizeBeforeCreate = bookingRepository.findAll().size();

        // Create the Booking

        restBookingMockMvc.perform(post("/api/bookings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(booking)))
                .andExpect(status().isCreated());

        // Validate the Booking in the database
        List<Booking> bookings = bookingRepository.findAll();
        assertThat(bookings).hasSize(databaseSizeBeforeCreate + 1);
        Booking testBooking = bookings.get(bookings.size() - 1);
        assertThat(testBooking.getBookingType()).isEqualTo(DEFAULT_BOOKING_TYPE);
        assertThat(testBooking.getBookingStatus()).isEqualTo(DEFAULT_BOOKING_STATUS);
        assertThat(testBooking.getWbs()).isEqualTo(DEFAULT_WBS);
        assertThat(testBooking.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBooking.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllBookings() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookings
        restBookingMockMvc.perform(get("/api/bookings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(booking.getId().intValue())))
                .andExpect(jsonPath("$.[*].bookingType").value(hasItem(DEFAULT_BOOKING_TYPE.toString())))
                .andExpect(jsonPath("$.[*].bookingStatus").value(hasItem(DEFAULT_BOOKING_STATUS.toString())))
                .andExpect(jsonPath("$.[*].wbs").value(hasItem(DEFAULT_WBS.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getBooking() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get the booking
        restBookingMockMvc.perform(get("/api/bookings/{id}", booking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(booking.getId().intValue()))
            .andExpect(jsonPath("$.bookingType").value(DEFAULT_BOOKING_TYPE.toString()))
            .andExpect(jsonPath("$.bookingStatus").value(DEFAULT_BOOKING_STATUS.toString()))
            .andExpect(jsonPath("$.wbs").value(DEFAULT_WBS.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBooking() throws Exception {
        // Get the booking
        restBookingMockMvc.perform(get("/api/bookings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBooking() throws Exception {
        // Initialize the database
        bookingService.save(booking);

        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Update the booking
        Booking updatedBooking = new Booking();
        updatedBooking.setId(booking.getId());
        updatedBooking.setBookingType(UPDATED_BOOKING_TYPE);
        updatedBooking.setBookingStatus(UPDATED_BOOKING_STATUS);
        updatedBooking.setWbs(UPDATED_WBS);
        updatedBooking.setStartDate(UPDATED_START_DATE);
        updatedBooking.setEndDate(UPDATED_END_DATE);

        restBookingMockMvc.perform(put("/api/bookings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBooking)))
                .andExpect(status().isOk());

        // Validate the Booking in the database
        List<Booking> bookings = bookingRepository.findAll();
        assertThat(bookings).hasSize(databaseSizeBeforeUpdate);
        Booking testBooking = bookings.get(bookings.size() - 1);
        assertThat(testBooking.getBookingType()).isEqualTo(UPDATED_BOOKING_TYPE);
        assertThat(testBooking.getBookingStatus()).isEqualTo(UPDATED_BOOKING_STATUS);
        assertThat(testBooking.getWbs()).isEqualTo(UPDATED_WBS);
        assertThat(testBooking.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBooking.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void deleteBooking() throws Exception {
        // Initialize the database
        bookingService.save(booking);

        int databaseSizeBeforeDelete = bookingRepository.findAll().size();

        // Get the booking
        restBookingMockMvc.perform(delete("/api/bookings/{id}", booking.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Booking> bookings = bookingRepository.findAll();
        assertThat(bookings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
