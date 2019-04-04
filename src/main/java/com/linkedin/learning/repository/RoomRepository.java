package com.linkedin.learning.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.linkedin.learning.entity.Room;

public interface RoomRepository extends CrudRepository<Room, Long>{

	Optional<Room> findById(Long id);
	
	
	
}
