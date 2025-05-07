package com.BookingRoom.UniversityBookingRoom.Repository;


import com.BookingRoom.UniversityBookingRoom.Model.Professor;
import com.BookingRoom.UniversityBookingRoom.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {

    List<Room> findAllByStatus(String available);
}
