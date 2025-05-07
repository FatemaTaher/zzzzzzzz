package com.BookingRoom.UniversityBookingRoom.Controller;

import com.BookingRoom.UniversityBookingRoom.Model.Course;
import com.BookingRoom.UniversityBookingRoom.Model.DTO.CourseNameDTO;
import com.BookingRoom.UniversityBookingRoom.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "allCourses", produces = "application/xml")
    public List<CourseNameDTO> getAllCourseNames() {
        return courseService.getAllCourses()
                .stream()
                .map(course -> new CourseNameDTO(course.getId(), course.getName()))
                .toList();
    }
}
