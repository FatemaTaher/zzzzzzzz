package com.BookingRoom.UniversityBookingRoom.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Entity
@Table
@Data
@NoArgsConstructor // Lombok هيتولى توليد constructor فاضي
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;

    @Column(name = "role", insertable = false, updatable = false)//الكولم هيتسجل لوحده
    private String role;


}
