package com.BookingRoom.UniversityBookingRoom.Repository;

import com.BookingRoom.UniversityBookingRoom.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String Name);
}
