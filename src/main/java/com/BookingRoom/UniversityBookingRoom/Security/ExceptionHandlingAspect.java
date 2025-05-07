package com.BookingRoom.UniversityBookingRoom.Security;


import com.BookingRoom.UniversityBookingRoom.Service.SimpleLogger;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {
@Autowired
SimpleLogger simpleLogger;

    @AfterThrowing(
            pointcut = "execution(* com.BookingRoom.UniversityBookingRoom.Service.*.*(..))",
            throwing = "ex"
    )
    public void logException(Exception ex) {
        String errorMessage = "EXCEPTION: " + ex.getClass().getSimpleName() +
                " | MESSAGE: " + ex.getMessage();
        simpleLogger.log(errorMessage);
    }
}