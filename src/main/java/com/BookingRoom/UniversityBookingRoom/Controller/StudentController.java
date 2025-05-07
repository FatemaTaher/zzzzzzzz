package com.BookingRoom.UniversityBookingRoom.Controller;


import com.BookingRoom.UniversityBookingRoom.Model.Student;
import com.BookingRoom.UniversityBookingRoom.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Endpoint لعرض تفاصيل الكورسات للطالب باستخدام studentId
    @GetMapping("/viewCourseDetails/{courseId}")
    public List<String> viewCourseDetails(@PathVariable Long courseId) {
        return Collections.singletonList(studentService.viewCourseDetails(courseId));
    }

    @PostMapping("/registerCourse/{studentId}")
    public String registerCourses(@PathVariable Long studentId, @RequestBody List<Long> courseIds) {
        return studentService.registerCourses(studentId, courseIds);
    }


    @DeleteMapping("/dropCourse/{courseId}/{studentId}")
    public String dropCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return studentService.dropCourse(studentId, courseId);
    }


    @GetMapping("/checkRegistery")
    public ResponseEntity<Boolean> checkRegistery(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {

        Boolean isRegistered = studentService.checkRegistery(studentId, courseId);
        return ResponseEntity.ok(isRegistered);
    }

}
