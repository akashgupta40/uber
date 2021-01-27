package com.code.uber.services;

import com.code.uber.entities.Driver;
import com.code.uber.enums.DriverStatus;
import com.code.uber.repositories.DriverRepository;
import com.code.uber.requests.LocationUpdationRequest;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverService {

    final DriverRepository driverRepository;

    public Driver addDriver(Driver driver) throws Exception {
        if (driverRepository.existsByEmailId(driver.getEmailId())) {
            throw new Exception("user with emailId "+ driver.getEmailId()+" already exists");
        }
        return driverRepository.save(driver);
    }

    public void updateDriverLocation(LocationUpdationRequest locationUpdationRequest)
        throws Exception {
        Long driverId = locationUpdationRequest.getUserId();
        Long latitude = locationUpdationRequest.getLatitude();
        Long longitude = locationUpdationRequest.getLongitude();

        Driver driver = driverRepository.findOneById(driverId);
        if(driver == null) {
            throw new Exception("Driver does not exist with id " + driverId);
        }
        driver.setLocation_latitude(latitude);
        driver.setLocation_longitude(longitude);
        driverRepository.save(driver);
    }

    public List<Driver> fetchAvailableDriversByRideTypeIdAndGridId(Long rideTypeId, Long gridId) {
        List<Driver> activeDrivers = driverRepository.findDriverByRideTypeIdAndGridIdAndStatus(rideTypeId,
            DriverStatus.FREE.name());
        return activeDrivers;
    }

    public List<Driver> getDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriver(Long id) {
        return driverRepository.findOneById(id);
    }

    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

}
