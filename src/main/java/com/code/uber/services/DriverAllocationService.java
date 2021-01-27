package com.code.uber.services;

import com.code.uber.entities.Driver;
import com.code.uber.entities.Ride;
import com.code.uber.enums.DriverStatus;
import com.code.uber.enums.RideStatus;
import com.code.uber.repositories.DriverRepository;
import com.code.uber.repositories.RideRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverAllocationService {

    final DriverService driverService;
    final DriverRepository driverRepository;
    final RideRepository rideRepository;

    @Async
    public void allocateDriverForRide(Ride ride) {
        Long rideTypeId  = ride.getRideTypeId();
        List<Driver> availableDrivers = driverService.fetchAvailableDriversByRideTypeIdAndGridId(rideTypeId, null);
        log.info("Fount suitable drivers "+ availableDrivers.size());
        //USE LOGIC TO SORT HERE
        if(availableDrivers.size()>0) {
            Driver d = availableDrivers.get(0);
            ride.setDriverId(d.getId());
            //acquire redis lock on driver
            assignDriverToRide(ride, d);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void assignDriverToRide(Ride ride, Driver driver) {
        ride.setDriverId(driver.getId());
        ride.setRideStatus(RideStatus.DRIVER_ALLOTED);
        driver.setDriverStatus(DriverStatus.RIDE_ONGOING);
        driverRepository.save(driver);
        rideRepository.save(ride);
        log.info("Allocated ride of user "+ ride.getUserId()+ " to driverId: "+ driver.getId());
    }

}
