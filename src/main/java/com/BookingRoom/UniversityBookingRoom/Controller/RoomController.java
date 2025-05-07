package com.BookingRoom.UniversityBookingRoom.Controller;

import com.BookingRoom.UniversityBookingRoom.Model.Room;
import com.BookingRoom.UniversityBookingRoom.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/rooms", produces = MediaType.APPLICATION_XML_VALUE)
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PutMapping(value = "/{id}/changeStatus", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> changeRoomStatus(@PathVariable Long id) {
        try {
            Room updatedRoom = roomService.changeRoomStatus(id);
            return ResponseEntity.ok(updatedRoom); // 200 OK + البيانات
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to change room status: " + e.getMessage());
        }
    }

    @GetMapping(value = "/{id}/viewStatus", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> viewRoomStatus(@PathVariable Long id) {
        try {
            String status = roomService.viewRoomStatus(id);
            return ResponseEntity.ok(status); // 200 OK + status
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Room not found or error occurred: " + e.getMessage());
        }
    }
}
