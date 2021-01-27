package com.code.uber.services;

import com.code.uber.entities.Driver;
import com.code.uber.entities.Ride;
import com.code.uber.enums.DriverStatus;
import com.code.uber.enums.RideStatus;
import com.code.uber.pojos.StateTransitionInfo;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RideStateManager {

    @Transactional(rollbackFor = Exception.class)
    public void transitionState(RideStatus fromState, RideStatus toState, Ride ride, Driver driver) throws Exception {
        StateTransitionInfo transitionInfo = fromState.getStateTransitionInfo();
        List<String> allowedStates = transitionInfo.getJumpStates();
        if (!CollectionUtils.isEmpty(allowedStates) && !allowedStates.contains(toState.name())) {
            throw new Exception(("Transition from status " + fromState.name() + " to state " + toState.name() + "is illegal"));
        }
        ride.setRideStatus(toState);
        onTransition(fromState, toState, ride, driver);
    }

    public void onTransition(RideStatus fromState, RideStatus toState, Ride ride, Driver driver) {
        switch (toState) {
            case RIDE_ENDED:
                driver.setDriverStatus(DriverStatus.FREE);
                //call payment hook
            case DRIVER_ALLOTED:

        }
    }

}
