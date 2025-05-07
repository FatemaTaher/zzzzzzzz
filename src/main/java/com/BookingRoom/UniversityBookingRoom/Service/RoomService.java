package com.BookingRoom.UniversityBookingRoom.Service;

import com.BookingRoom.UniversityBookingRoom.Model.DTO.CourseDTO;
import com.BookingRoom.UniversityBookingRoom.Model.DTO.ReservationDTO;
import com.BookingRoom.UniversityBookingRoom.Model.Reservation;
import com.BookingRoom.UniversityBookingRoom.Model.Room;
import com.BookingRoom.UniversityBookingRoom.Repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class RoomService {

    @Autowired
    private RoomRepo roomrepo;


    public Room changeRoomStatus(Long id) {
        Optional<Room> existing = roomrepo.findById(id);
        if (existing.isPresent()) {
            Room room = existing.get();

            if (room.getStatus().equals("available"))
                room.setStatus("not available");
            else if (room.getStatus().equals("not available"))
                room.setStatus("available");

            room.setNumber(room.getNumber());
            room.setCapacity(room.getCapacity());


            return roomrepo.save(room);
        } else {
            return null;
        }
    }


        public String viewRoomStatus (Long id){
            Optional<Room> existing = roomrepo.findById(id);
            if (existing.isPresent()) {
                Room room = existing.get();

                    return room.getStatus();


            }

            return "Room Not Found!";
        }


    public List<Room> viewAllAvailableRooms() {
        Optional<List<Room>> optionalRooms = Optional.ofNullable(roomrepo.findAllByStatus("available"));
        return optionalRooms.orElseGet(Collections::emptyList);
    }






}