package com.BookingRoom.UniversityBookingRoom.Repository;


import com.BookingRoom.UniversityBookingRoom.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {

}
