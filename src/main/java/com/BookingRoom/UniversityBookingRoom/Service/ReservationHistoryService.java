package com.BookingRoom.UniversityBookingRoom.Service;

import com.BookingRoom.UniversityBookingRoom.Model.DTO.ReservationHistoryDTO;
import com.BookingRoom.UniversityBookingRoom.Model.Professor;
import com.BookingRoom.UniversityBookingRoom.Model.Reservation;
import com.BookingRoom.UniversityBookingRoom.Model.ReservationHistory;
import com.BookingRoom.UniversityBookingRoom.Repository.ProfessorRepo;
import com.BookingRoom.UniversityBookingRoom.Repository.ReservationHistoryRepository;
import com.BookingRoom.UniversityBookingRoom.Repository.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationHistoryService {

    @Autowired
    private ReservationHistoryRepository historyRepository;
    @Autowired
    private ProfessorRepo professorRepo;
    @Autowired
    private ReservationRepo reservationRepo;

    public void logHistory(Reservation reservation, String status, String actionBy, String comment) {

        ReservationHistory history = new ReservationHistory();
        history.setReservation(reservation);
        history.setStatus(status);
        history.setActionTime(LocalDateTime.now());
        history.setActionBy(actionBy);
        history.setComment(comment);
        historyRepository.save(history);
    }

    public List<ReservationHistoryDTO> getHistoryForAllReservation(Long professorId) {
        // 1. Get all reservations by professorId
        List<Reservation> reservations = reservationRepo.findByProfessorId(professorId);

        if (reservations.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. Extract reservation IDs
        List<Long> reservationIds = reservations.stream()
                .map(Reservation::getId)
                .toList();

        // 3. Get all reservation history entries that match these reservation IDs
        List<ReservationHistory> histories = historyRepository.findByReservationIdIn(reservationIds);

        // 4. Convert to DTOs
        return histories.stream()
                .map(ReservationHistoryDTO::new)
                .toList();
    }



    public List<ReservationHistory> getHistoryForReservation(Long reservationId) {
        return historyRepository.findByReservationIdOrderByActionTimeAsc(reservationId);
    }
}
