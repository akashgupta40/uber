package com.code.uber.entities;

import com.code.uber.enums.RideStatus;
import java.util.Date;
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
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "rides")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "driver_id")
    Long driverId;

    @Column(name = "source_location_latitude")
    String sourceLocationLatitude;

    @Column(name = "source_location_longitude")
    String sourceLocationLongitude;

    @Column(name = "dest_location_latitude")
    String destLocationLatitude;

    @Column(name = "dest_location_longitude")
    String destLocationLongitude;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    RideStatus rideStatus;

    @Column(name="start_time")
    Date startTime;

    @Column(name="end_time")
    Date endTime;

    @Column(name="ride_type_id")
    Long rideTypeId;

    public Ride(Long userId, String sourceLocationLatitude,
        String sourceLocationLongitude, String destLocationLatitude,
        String destLocationLongitude, RideStatus rideStatus, Date startTime,
        Long rideTypeId) {
        this.userId = userId;
        this.sourceLocationLatitude = sourceLocationLatitude;
        this.sourceLocationLongitude = sourceLocationLongitude;
        this.destLocationLatitude = destLocationLatitude;
        this.destLocationLongitude = destLocationLongitude;
        this.rideStatus = rideStatus;
        this.startTime = startTime;
        this.rideTypeId = rideTypeId;
    }
}
