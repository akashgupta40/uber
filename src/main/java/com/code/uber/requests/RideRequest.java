package com.code.uber.requests;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RideRequest {

    long userId;

    @NotNull
    String sourceLocationLatitude;

    @NotNull
    String sourceLocationLongitude;

    String destLocationLatitude;
    String destLocationLongitude;

    long rideTypeId;

}
