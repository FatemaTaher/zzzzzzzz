package com.BookingRoom.UniversityBookingRoom.Model.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

@Data
@JacksonXmlRootElement(localName = "reservationRequest")
public class ReservationRequestDTO {

    private Long roomID;
    private Integer nofStudents;
    private Long courseID;
    private Long professorID;
    private Long adminID;
    private Date time;
}
