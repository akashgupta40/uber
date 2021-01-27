package com.code.uber.controllers;

import com.code.uber.entities.Ride;
import com.code.uber.requests.RideRequest;
import com.code.uber.requests.RideStatusUpdateRequest;
import com.code.uber.services.RideService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ride")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RideController {

    final RideService rideService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addRide(@RequestBody RideRequest rideRequest) {
        try {
            return new ResponseEntity<>(rideService.createRide(rideRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error while creating ride for user " + rideRequest.getUserId(), e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getRides(@RequestParam(value = "userId") Long userId) {
        try {
            return new ResponseEntity<>(rideService.getRidesForUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while getting rides for user " + userId, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/latest")
    public ResponseEntity<?> getLatestRideInfo(@RequestParam(value = "userId") Long userId) {
        try {
            return new ResponseEntity<>(rideService.getLatestRideForUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while getting latest ride for user " + userId, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update/status")
    public ResponseEntity<?> updateRideStatus(@RequestBody RideStatusUpdateRequest rideStatusUpdateRequest) {
        try {
            rideService.updateRideStatus(rideStatusUpdateRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error for " + rideStatusUpdateRequest.getRideId(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
