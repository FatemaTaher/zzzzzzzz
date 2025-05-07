package com.BookingRoom.UniversityBookingRoom.Model.DTO;

import com.BookingRoom.UniversityBookingRoom.Model.ReservationHistory;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "reservationhistoryDTO")
public class ReservationHistoryDTO {
    private String status;
    private Long reservationId;

    private LocalDateTime actionTime;
    private String actionBy;
    private String comment;

    public ReservationHistoryDTO(ReservationHistory history) {
        this.reservationId = history.getReservation().getId();
        this.status = history.getStatus();
        this.actionTime = history.getActionTime();
        this.actionBy = history.getActionBy();
        this.comment = history.getComment();
    }

}
