package com.code.uber.repositories;

import com.code.uber.entities.Ride;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends CrudRepository<Ride, Long> {
    List<Ride> findAllByUserIdOrderByStartTimeDesc(Long userId);
}
