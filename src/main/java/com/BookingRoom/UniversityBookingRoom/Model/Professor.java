
package com.BookingRoom.UniversityBookingRoom.Model;

        import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
        import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
        import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
        import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.util.ArrayList;
        import java.util.List;

@JacksonXmlRootElement(localName = "professor")
@DiscriminatorValue("professor")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Professor extends User{



    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

}
