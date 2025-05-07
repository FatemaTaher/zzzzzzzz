package com.BookingRoom.UniversityBookingRoom.Controller;

import com.BookingRoom.UniversityBookingRoom.Model.DTO.ReservationHistoryDTO;
import com.BookingRoom.UniversityBookingRoom.Model.DTO.ReservationRequestDTO;
import com.BookingRoom.UniversityBookingRoom.Model.DTO.ReservationRequestDTO;
import com.BookingRoom.UniversityBookingRoom.Model.Reservation;
import com.BookingRoom.UniversityBookingRoom.Model.ReservationHistory;
import com.BookingRoom.UniversityBookingRoom.Model.Room;
import com.BookingRoom.UniversityBookingRoom.Service.ProfessorService;
import com.BookingRoom.UniversityBookingRoom.Service.ReservationHistoryService;
import com.BookingRoom.UniversityBookingRoom.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/professors")
public class ProfessorController {

    @Autowired
    private ReservationHistoryService reservationHistoryService; // إضافة الـ @Autowired هنا

    @Autowired
    private ProfessorService professorService;



    @Autowired
    private RoomService roomService;



    @PostMapping(value = "/reserveRoom", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createReservation(@RequestBody ReservationRequestDTO requestDTO) {
        try {
            professorService.reserveRoom(
                    requestDTO.getRoomID(),
                    requestDTO.getNofStudents(),
                    requestDTO.getCourseID(),
                    requestDTO.getProfessorID(),
                    requestDTO.getTime()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Reservation created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create reservation: " + e.getMessage());
        }
    }


    @DeleteMapping(value = "/cancelReservation/{reservationID}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationID) {
        try {
            boolean isCancelled = professorService.cancelReservation(reservationID);

            if (isCancelled) {
                return ResponseEntity.ok("Reservation cancelled successfully.");
            } else {
                return ResponseEntity.status(404).body("Reservation not found.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to cancel reservation: " + e.getMessage());
        }
    }

    @GetMapping(value = "/reservationStatus/{reservationID}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> viewReservationStatus(@PathVariable Long reservationID) {
        try {
            String status = professorService.viewReservationStatus(reservationID);

            if (status.equals("Not Found!")) {
                return ResponseEntity.status(404).body("Reservation not found.");
            } else {
                return ResponseEntity.ok("Reservation status: " + status);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to retrieve status: " + e.getMessage());
        }
    }

    @GetMapping("/reservationsHistory/{reservationId}")
    public List<ReservationHistoryDTO> getReservationHistory(@PathVariable Long reservationId) {

        List<ReservationHistory> historyList = reservationHistoryService.getHistoryForReservation(reservationId);
        return historyList.stream().map(ReservationHistoryDTO::new).collect(Collectors.toList());
    }


    @GetMapping("/allReservationsHistory/{professorId}")
    public List<ReservationHistoryDTO> getAllReservationHistory(@PathVariable Long professorId) {
        List<ReservationHistoryDTO> historyList = reservationHistoryService.getHistoryForAllReservation(professorId);
        return historyList;
    }


    @GetMapping("/allAvailableRooms")
    public List<Room> getAllAvailableRooms() {

        List<Room> rooms = roomService.viewAllAvailableRooms();
        return rooms;
    }


}
