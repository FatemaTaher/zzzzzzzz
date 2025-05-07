package com.BookingRoom.UniversityBookingRoom.Repository;


import com.BookingRoom.UniversityBookingRoom.Model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepo extends JpaRepository<Professor, Long> {
}
