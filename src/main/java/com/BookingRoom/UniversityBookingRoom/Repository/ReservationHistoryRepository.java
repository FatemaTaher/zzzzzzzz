package com.BookingRoom.UniversityBookingRoom.Repository;

import com.BookingRoom.UniversityBookingRoom.Model.Reservation;
import com.BookingRoom.UniversityBookingRoom.Model.ReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory, Long> {
    List<ReservationHistory> findByReservationIdOrderByActionTimeAsc(Long reservationId);
    boolean existsByIdAndStatus(Long id, String status);


    List<ReservationHistory> findByActionBy(String actionBy);


    void deleteByReservationId(Long reservationID);

    List<ReservationHistory> findByReservationIdIn(List<Long> reservationIds);
}
