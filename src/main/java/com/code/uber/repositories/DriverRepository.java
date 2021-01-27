package com.code.uber.repositories;

import com.code.uber.entities.Driver;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> {

    boolean existsByEmailId(String emailId);

    Driver findOneById(Long id);
    List<Driver> findAll();

    @Query(nativeQuery = true, value = "select * from drivers d, driver_ride_type_map drtm where drtm.ride_type_id = :rideTypeId and d.status = :status")
    List<Driver> findDriverByRideTypeIdAndGridIdAndStatus(@Param("rideTypeId") Long rideTypeId,  @Param("status") String status);

}
