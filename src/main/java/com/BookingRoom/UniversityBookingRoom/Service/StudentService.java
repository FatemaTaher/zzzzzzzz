package com.BookingRoom.UniversityBookingRoom.Service;

import com.BookingRoom.UniversityBookingRoom.Model.Course;
import com.BookingRoom.UniversityBookingRoom.Model.DTO.CourseDTO;
import com.BookingRoom.UniversityBookingRoom.Model.DTO.ReservationDTO;
import com.BookingRoom.UniversityBookingRoom.Model.Reservation;
import com.BookingRoom.UniversityBookingRoom.Model.Room;
import com.BookingRoom.UniversityBookingRoom.Model.Student;
import com.BookingRoom.UniversityBookingRoom.Repository.CourseRepo;
import com.BookingRoom.UniversityBookingRoom.Repository.ReservationRepo;
import com.BookingRoom.UniversityBookingRoom.Repository.RoomRepo;
import com.BookingRoom.UniversityBookingRoom.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ReservationRepo reservationRepo;
    public String viewCourseDetails(Long courseId) {
        Optional<Course> courseOptional = courseRepo.findById(courseId);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            Reservation reservation = reservationRepo.findByCourseIdAndStatus(course.getId(), "accepted");

            if (reservation != null) {
                return "Course ID: " + course.getId() +
                        ", Professor: " + reservation.getProfessor().getName() +
                        ", Room: " + reservation.getRoom().getNumber() +
                        ", Time: " + reservation.getTime();
            } else {
                return "Course ID: " + course.getId() + " - Not assigned yet";
            }
        } else {
            return "Course with ID " + courseId + " not found.";
        }
    }


    public Boolean checkRegistery(Long studentId, Long courseId) {
        Optional<Student> studentOptional = studentRepo.findById(studentId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return student.getCourses()
                    .stream()
                    .anyMatch(course -> course.getId().equals(courseId));
        }

        return false;
    }


    public String registerCourses(Long studentId, List<Long> courseIds) {
        Student student = studentRepo.findById(studentId).orElse(null);
        if (student == null) {
            return "Student not found!";
        }

        List<Course> coursesToAdd = new ArrayList<>();

        for (Long courseId : courseIds) {
            boolean alreadyRegistered = student.getCourses().stream()
                    .anyMatch(c -> c.getId().equals(courseId));
            if (!alreadyRegistered) {
                Course course = courseRepo.findById(courseId).orElse(null);
                if (course != null) {
                    coursesToAdd.add(course);
                }
            }
        }

        if (coursesToAdd.isEmpty()) {
            return "No new courses to register.";
        }

        student.getCourses().addAll(coursesToAdd);
        studentRepo.save(student);

        return "Courses registered successfully!";
    }


    public String dropCourse(Long studentId, Long courseId) {
        Student student = studentRepo.findById(studentId).orElse(null);
        if (student == null) {
            return "Student not found!";
        }

        Course courseToRemove = courseRepo.findById(courseId).orElse(null);
        if (courseToRemove == null) {
            return "Course not found!";
        }

        boolean wasEnrolled = student.getCourses().removeIf(course -> course.getId().equals(courseId));

        if (wasEnrolled) {
            studentRepo.save(student);
            return "Course dropped successfully!";
        } else {
            return "Student is not enrolled in this course.";
        }
    }


}