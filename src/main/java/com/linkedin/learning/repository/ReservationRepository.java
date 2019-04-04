package com.linkedin.learning.repository;

import org.springframework.data.repository.CrudRepository;

import com.linkedin.learning.entity.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	

}
