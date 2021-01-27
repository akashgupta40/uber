package com.code.uber.controllers;

import com.code.uber.entities.Driver;
import com.code.uber.requests.LocationUpdationRequest;
import com.code.uber.services.DriverService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/driver")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverController {

    final DriverService driverService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addDriver(@RequestBody Driver driver) {
        try {
            return new ResponseEntity<>(driverService.addDriver(driver), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update-location")
    public ResponseEntity<?> updateLocationOfDriver(@RequestBody LocationUpdationRequest locationUpdationRequest) {
        try {
            driverService.updateDriverLocation(locationUpdationRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public ResponseEntity<?> getDriver(@PathVariable("id") Long id) {
        return new ResponseEntity<>(driverService.getDriver(id), HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getDrivers() {
        return new ResponseEntity<>(driverService.getDrivers(), HttpStatus.ACCEPTED);
    }

}
