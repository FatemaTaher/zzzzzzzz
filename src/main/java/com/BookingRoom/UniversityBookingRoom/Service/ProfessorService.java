package com.BookingRoom.UniversityBookingRoom.Service;

import com.BookingRoom.UniversityBookingRoom.Model.*;
import com.BookingRoom.UniversityBookingRoom.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ReservationRepo reservationrepo;

    @Autowired
    private ProfessorRepo professorrepo;

    @Autowired
    private RoomRepo roomrepo;

    @Autowired
    private CourseRepo courserepo;

    @Autowired
    private AdminRepo adminrepo;

    @Autowired
    private ReservationHistoryRepository reservationHistoryRepository;

    @Autowired
    private ReservationHistoryService reservationHistoryService;

    public Reservation reserveRoom(Long roomID, Integer NofStudents, Long courseID, Long professorID, Date time) {
        // أولًا: نبحث عن الحجزات القائمة للكورس ده
        List<Reservation> existingReservations = reservationrepo.findByCourseId(courseID);

        // التحقق من وجود حجز بنفس الغرفة، الوقت، والكورس، وحالته pending أو accepted
        for (Reservation r : existingReservations) {
            boolean sameRoom = r.getRoom().getId().equals(roomID);
            //boolean sameTime = r.getTime().equals(time);
            boolean sameCourse = r.getCourse().getId().equals(courseID);
            boolean pendingOrAccepted = r.getStatus().equalsIgnoreCase("b") || r.getStatus().equalsIgnoreCase("accepted");

            if (sameRoom && sameCourse && pendingOrAccepted) {
                throw new RuntimeException("There is already a reservation for this course, time, and room that is Pending or Accepted!");
            }
        }

        // لو مفيش مشكلة في الحجز، أكمل العملية
        Optional<Room> roomOpt = roomrepo.findById(roomID);
        Optional<Course> courseOpt = courserepo.findById(courseID);
        Optional<Professor> profOpt = professorrepo.findById(professorID);

        if (roomOpt.isPresent() && courseOpt.isPresent() && profOpt.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setNofStudents(NofStudents);
            reservation.setTime(time);
            reservation.setRoom(roomOpt.get());
            reservation.setCourse(courseOpt.get());
            reservation.setProfessor(profOpt.get());
            reservation.setStatus("pending");

            Reservation savedReservation = reservationrepo.save(reservation);

            Professor professor = profOpt.get();
            reservationHistoryService.logHistory(savedReservation, "pending", professor.getName(), " ");

            return savedReservation;
        }

        throw new RuntimeException("One or more entities not found!");
    }

    public boolean cancelReservation(Long reservationID) {
        Optional<Reservation> optionalReservation = reservationrepo.findById(reservationID);

        boolean alreadyCanceled = reservationHistoryRepository.existsByIdAndStatus(reservationID, "canceled");
        if (alreadyCanceled) {
            return false;
        }

        if (optionalReservation.isPresent()) {
            Reservation res = optionalReservation.get();
            Professor professor = res.getProfessor();

            // سجل الحدث في الهيستوري
            reservationHistoryService.logHistory(res, "canceled", professor.getName(), " ");

            // امسح كل الهيستوري المرتبط بالحجز
            reservationHistoryRepository.deleteByReservationId(reservationID);

            // امسح الحجز نفسه
            reservationrepo.deleteById(reservationID);
            return true;
        }

        return false;
    }

    public String viewReservationStatus(Long reservationID) {
        Optional<Reservation> reservation = reservationrepo.findById(reservationID);

        if (reservation.isPresent()) {
            Reservation res = reservation.get();
            return res.getStatus();
        } else {
            return "Not Found!";
        }
    }
}
