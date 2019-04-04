package com.linkedin.learning.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.linkedin.learning.entity.Reservation;
import com.linkedin.learning.model.response.ReservationResponse;

@Component
public class ReservationEntityToReservationResponseConverter implements Function<Reservation, ReservationResponse> {

	@Override
	public ReservationResponse apply(Reservation t) {

		ReservationResponse reservationResponse = new ReservationResponse();
		reservationResponse.setId(t.getId());
		
		reservationResponse.setCheckin(t.getCheckin());
		reservationResponse.setCheckout(t.getCheckout());
		return reservationResponse;
	}

}
