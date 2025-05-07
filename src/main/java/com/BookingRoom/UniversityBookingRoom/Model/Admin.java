package com.BookingRoom.UniversityBookingRoom.Model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("admin")
@Data
@Builder
@JacksonXmlRootElement(localName = "admins")
public class Admin extends User {
    // لا يوجد constructor فاضي إضافي هنا
}
