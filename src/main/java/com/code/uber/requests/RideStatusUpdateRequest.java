package com.code.uber.requests;

import com.code.uber.enums.RideStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RideStatusUpdateRequest {

    long rideId;
    RideStatus status;

}
