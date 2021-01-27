package com.code.uber.services;

import com.code.uber.entities.Driver;
import com.code.uber.entities.Ride;
import com.code.uber.enums.RideStatus;
import com.code.uber.repositories.RideRepository;
import com.code.uber.requests.RideRequest;
import com.code.uber.requests.RideStatusUpdateRequest;
import com.code.uber.responses.RideResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RideService {

    final RideRepository rideRepository;
    final DriverAllocationService driverAllocationService;
    final RideStateManager rideStateManager;
    final DriverService driverService;

    public Ride createRide(RideRequest rideRequest) throws Exception {
        validate(rideRequest);
        Ride ride = new Ride(rideRequest.getUserId(), rideRequest.getSourceLocationLatitude(),
            rideRequest.getSourceLocationLongitude(), rideRequest.getDestLocationLatitude(),
            rideRequest.getDestLocationLongitude(), RideStatus.ALLOTING_DRIVER, new Date(), rideRequest.getRideTypeId());
        ride = rideRepository.save(ride);
        driverAllocationService.allocateDriverForRide(ride);
        return ride;
    }

    public List<Ride> getRidesForUser(Long userId) {
        return rideRepository.findAllByUserIdOrderByStartTimeDesc(userId);
    }

    public Ride getLatestRideForUser(Long userId) {
        List<Ride> rides = getRidesForUser(userId);
        if(!CollectionUtils.isEmpty(rides)) {
            return rides.get(0);
        }
        return null;
    }

    private void validate(RideRequest rideRequest) throws Exception {
        Ride ride = getLatestRideForUser(rideRequest.getUserId());
        if(ride!=null && !Arrays.asList(RideStatus.RIDE_ENDED, RideStatus.RIDE_CANCELLED).contains(ride.getRideStatus())) {
            throw new Exception("Ride is already in progress for user id: " + rideRequest.getUserId());
        }
    }

    public void updateRideStatus(RideStatusUpdateRequest rideStatusUpdateRequest) throws Exception {
        Long rideId = rideStatusUpdateRequest.getRideId();
        Optional<Ride> oRide = rideRepository.findById(rideId);
        if (!oRide.isPresent()) {
            throw new Exception("Ride id " + rideId + " is not valid");
        }
        Ride ride = oRide.get();
        Long driverId = ride.getDriverId();
        Driver driver  = driverService.getDriver(driverId);
        rideStateManager.transitionState(ride.getRideStatus(), rideStatusUpdateRequest.getStatus(), ride, driver);
        rideRepository.save(ride);
        driverService.saveDriver(driver);
    }

}
