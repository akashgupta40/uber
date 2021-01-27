package com.code.uber.entities;


import com.code.uber.Person;
import com.code.uber.enums.DriverStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "drivers")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Driver {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;


    @Column(name = "mobile_no")
    String mobileNo;

    @Column(name = "email_id")
    String emailId;

    @Column(name = "vehicle")
    String vehicle;

    @Column(name = "location_latitude")
    Long location_latitude;

    @Column(name = "location_longitude")
    Long location_longitude;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    DriverStatus driverStatus;

}
