package com.linkedin.learning.converter;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linkedin.learning.entity.Reservation;
import com.linkedin.learning.entity.Room;
import com.linkedin.learning.model.request.ReservationRequest;
import com.linkedin.learning.repository.RoomRepository;

@Component
public class ReservationRequestToReservationEntityConverter implements Function<ReservationRequest, Reservation> {

	@Autowired
	RoomRepository roomRepository;
	
	@Override
	public Reservation apply(ReservationRequest t) {

		Reservation reservation = new Reservation();
		reservation.setCheckin(t.getCheckin());
		reservation.setCheckout(t.getCheckout());
		if(t.getRoomId() != null) {
			Room room = roomRepository.findById(t.getRoomId()).orElse(new Room());
			reservation.setRoom(room);
		}
		return reservation;
	}

}
