package com.learnauth.auth_service.models;


import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking extends BaseModel {
    /*
        One to One -> default fetch type -> Eager
        One to Many -> default fetch type -> Lazy
        Many to Many -> defdult fetch type -> Lazy
        Many to One -> default fetch type -> Eager
     */

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(value = TemporalType.TIMESTAMP)  // telling spring that set this value as Timestamp
    private Date endTime;

    private Long totalDistance;

    @Enumerated(value  = EnumType.STRING)
    private BookingStatus bookingStatus;

    @ManyToOne
    // FK column in db
    private Driver driver;

    @ManyToOne
    private Passenger passenger;
}
