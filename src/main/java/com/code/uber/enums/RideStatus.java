package com.code.uber.enums;

import com.code.uber.pojos.StateTransitionInfo;
import java.util.Arrays;

public enum RideStatus {
    RIDE_CANCELLED(new StateTransitionInfo(5)),
    RIDE_ENDED(new StateTransitionInfo(4, true)),
    RIDE_STARTED(new StateTransitionInfo(3,  Arrays.asList( RIDE_ENDED.name()))),
    DRIVER_REACHED(new StateTransitionInfo(2,  Arrays.asList(RIDE_CANCELLED.name(), RIDE_STARTED.name()))),
    DRIVER_ALLOTED(new StateTransitionInfo(1,  Arrays.asList(RIDE_CANCELLED.name(), DRIVER_REACHED.name()))),
    ALLOTING_DRIVER(new StateTransitionInfo(0,  Arrays.asList(RIDE_CANCELLED.name(), DRIVER_ALLOTED.name())));


    private StateTransitionInfo stateTransitionInfo;

    RideStatus(StateTransitionInfo stateTransitionInfo) {
        this.stateTransitionInfo = stateTransitionInfo;
        this.stateTransitionInfo.setStateName(this.name());
    }


    public StateTransitionInfo getStateTransitionInfo() {
        return stateTransitionInfo;
    }
}
