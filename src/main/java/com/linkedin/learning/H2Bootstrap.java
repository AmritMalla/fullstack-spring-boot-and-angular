package com.linkedin.learning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.linkedin.learning.entity.Room;
import com.linkedin.learning.repository.RoomRepository;

@Component
public class H2Bootstrap implements CommandLineRunner{
	
	@Autowired
	RoomRepository roomRepository;

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Bootstraping data: h2");
		
		roomRepository.save(new Room(405,"40"));
		roomRepository.save(new Room(406,"100"));
		roomRepository.save(new Room(407,"110"));
		
		Iterable<Room> all = roomRepository.findAll();
		for(Room r:all) {
			System.out.println(r.getRoomNumber());
		}
	}

}
