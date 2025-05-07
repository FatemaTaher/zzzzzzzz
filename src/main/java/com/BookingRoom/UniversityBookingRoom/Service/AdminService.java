package com.BookingRoom.UniversityBookingRoom.Service;

import com.BookingRoom.UniversityBookingRoom.Model.DTO.CourseDTO;
import com.BookingRoom.UniversityBookingRoom.Model.DTO.ReservationDTO;
import com.BookingRoom.UniversityBookingRoom.Model.Reservation;
import com.BookingRoom.UniversityBookingRoom.Model.Room;
import com.BookingRoom.UniversityBookingRoom.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AdminService {

    @Autowired
    private AdminRepo adminrepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private ReservationHistoryRepository reservationHistoryRepository;



    @Autowired
    private RoomRepo roomrepo;

    public String startNewRound() {

        reservationHistoryRepository.deleteAll();

        List<Reservation> reservations = reservationRepo.findAll();
        reservations.forEach(reservation -> {
            reservation.setCourse(null);
            reservation.setProfessor(null);
            reservation.setRoom(null);
        });
        reservationRepo.saveAll(reservations);
        reservationRepo.deleteAll();

        studentRepo.findAll().forEach(student -> student.getCourses().clear());
        studentRepo.saveAll(studentRepo.findAll());

        return "New round started successfully! All reservations and student-course registrations have been cleared.";
    }

    public Room addRoom(Room room) {
        return roomrepo.save(room);
    }



    public List<ReservationDTO> viewPendingReservations() {
        // استخدم DISTINCT لتجنب التكرار
        List<Reservation> reservations = reservationRepo.findAllDistinctPending("pending");

        return reservations.stream()
                .map(reservation -> {
                    // تحقق من وجود الـ Course لتجنب NullPointerException
                    String courseName = (reservation.getCourse() != null) ?
                            reservation.getCourse().getName() :
                            "No Course";
                    return new ReservationDTO(
                            reservation.getId(),
                            reservation.getStatus(),
                            reservation.getTime(),
                            new CourseDTO(courseName)
                    );
                })
                .toList();
    }



    }