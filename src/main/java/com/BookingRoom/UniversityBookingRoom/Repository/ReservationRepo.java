package com.BookingRoom.UniversityBookingRoom.Repository;


import com.BookingRoom.UniversityBookingRoom.Model.Course;
import com.BookingRoom.UniversityBookingRoom.Model.Professor;
import com.BookingRoom.UniversityBookingRoom.Model.Reservation;
import com.BookingRoom.UniversityBookingRoom.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {


    boolean existsByRoomIdAndTimeAndStatus(Long roomId, Date time, String status);

    List<Reservation> findAllByStatus(String status);

    Reservation findByCourseIdAndStatus(Long courseId, String status);
    boolean existsByRoomAndStatusIn(Room room, List<String> statuses);


    boolean existsByRoomAndTimeAndStatusIn(Room room, Date time, List<String> pending);

    List<Reservation> findByCourseId(Long courseID);

    List<Reservation> findDistinctByStatus(String pending);

    @Query("SELECT DISTINCT r FROM Reservation r WHERE r.status = :status")
    List<Reservation> findAllDistinctPending(@Param("status") String status);



    boolean existsByCourseAndStatus(Course course, String accepted);

    List<Reservation> findByProfessorId(Long professorId);
}
