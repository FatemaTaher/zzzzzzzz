package com.BookingRoom.UniversityBookingRoom.Controller;

import com.BookingRoom.UniversityBookingRoom.Model.DTO.ReservationDTO;
import com.BookingRoom.UniversityBookingRoom.Model.Reservation;
import com.BookingRoom.UniversityBookingRoom.Model.Room;
import com.BookingRoom.UniversityBookingRoom.Service.AdminService;
import com.BookingRoom.UniversityBookingRoom.Service.ReservationService;
import com.BookingRoom.UniversityBookingRoom.Service.StudentService;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/admins", produces = MediaType.APPLICATION_XML_VALUE)
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/addRoom", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> addRoom(@RequestBody Room room) {
        try {
            Room savedRoom = adminService.addRoom(room);
            return ResponseEntity.ok(savedRoom); // Status 200 +
            //return ResponseEntity.status(201).body(savedRoom);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add room: " + e.getMessage());
        }
    }

    //function in reservation service
    @PutMapping("/updateReservationStatus/{id}")
    public ResponseEntity<String> updateReservationStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        boolean updated = reservationService.updateReservationStatus(id, status);

        if (updated) {
            return ResponseEntity.ok("Reservation status updated to " + status);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update status. Room capacity may be insufficient or time already booked.");
        }
    }


    @GetMapping("/viewPendingReservation")
    public List<ReservationDTO> getPendingReservations() {
        return adminService.viewPendingReservations();
    }



    @PostMapping("/startNewRound")
    public String startNewRound() {
        return adminService.startNewRound();
    }
}
