package com.BookingRoom.UniversityBookingRoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableAspectJAutoProxy
@SpringBootApplication
public class UniversityBookingRoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityBookingRoomApplication.class, args);
	}

}
