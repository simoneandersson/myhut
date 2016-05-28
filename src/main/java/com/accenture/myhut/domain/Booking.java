package com.accenture.myhut.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.accenture.myhut.domain.enumeration.BookingType;

import com.accenture.myhut.domain.enumeration.BookingStatus;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_type")
    private BookingType bookingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus bookingStatus;

    @Column(name = "wbs")
    private String wbs;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    private Cabin cabinId;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getWbs() {
        return wbs;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Cabin getCabinId() {
        return cabinId;
    }

    public void setCabinId(Cabin cabin) {
        this.cabinId = cabin;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking booking = (Booking) o;
        if(booking.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + id +
            ", bookingType='" + bookingType + "'" +
            ", bookingStatus='" + bookingStatus + "'" +
            ", wbs='" + wbs + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
