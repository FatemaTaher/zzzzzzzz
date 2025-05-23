package com.BookingRoom.UniversityBookingRoom.Repository;


import com.BookingRoom.UniversityBookingRoom.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
}
