package com.BookingRoom.UniversityBookingRoom.Service;

import com.BookingRoom.UniversityBookingRoom.Model.Reservation;
import com.BookingRoom.UniversityBookingRoom.Model.Room;
import com.BookingRoom.UniversityBookingRoom.Repository.AdminRepo;
import com.BookingRoom.UniversityBookingRoom.Repository.ReservationRepo;
import com.BookingRoom.UniversityBookingRoom.Repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ReservationService {

    @Autowired
    private ReservationHistoryService reservationHistoryService;


    @Autowired
    private ReservationRepo reservationrepo;

    @Autowired
    private RoomRepo roomrepo;

    public Reservation addReservation(Reservation reservation) {
        return reservationrepo.save(reservation);
    }


    //used in admin controller
    public boolean updateReservationStatus(Long reservationId, String newStatus) {
        Optional<Reservation> optionalReservation = reservationrepo.findById(reservationId);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            Room room = reservation.getRoom();

            String actionBy = "Admin123";
            String comment = "";

            if ("accepted".equalsIgnoreCase(newStatus)) {

                if (reservation.getNofStudents() > room.getCapacity()) {
                    return false;
                }

                boolean isTimeConflict = reservationrepo.existsByRoomIdAndTimeAndStatus(
                        room.getId(), reservation.getTime(), "accepted"
                );

                if (isTimeConflict) {
                    return false;
                }

                boolean isCourseReserved = reservationrepo.existsByCourseAndStatus(reservation.getCourse(), "accepted");
                if (isCourseReserved) {
                    return false;
                }

                reservation.setStatus("accepted");
                room.setStatus("not available");
                roomrepo.save(room);

                comment = "Reservation accepted and room marked as not available";

            } else if ("rejected".equalsIgnoreCase(newStatus)) {
                reservation.setStatus("rejected");
                comment = "Reservation rejected by admin";
            }

            reservationrepo.save(reservation);

            reservationHistoryService.logHistory(reservation, newStatus.toLowerCase(), actionBy, comment);

            return true;
        }

        return false;
    }




}
