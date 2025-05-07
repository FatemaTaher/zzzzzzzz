package com.BookingRoom.UniversityBookingRoom.Repository;

import com.BookingRoom.UniversityBookingRoom.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Long> {


}
