package com.BookingRoom.UniversityBookingRoom.Model.DTO;

import java.util.Date;


public class ReservationDTO {

    private Long id;
    private String status;
    private Date time;
    private CourseDTO course;  // هنا نخلي الكورس فقط فيه الاسم

    public ReservationDTO(Long id, String status, Date time, CourseDTO course) {
        this.id = id;
        this.status = status;
        this.time = time;
        this.course = course;
    }

    // getters
    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Date getTime() {
        return time;
    }

    public CourseDTO getCourse() {
        return course;
    }
}