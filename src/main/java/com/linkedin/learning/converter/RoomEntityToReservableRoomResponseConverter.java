package com.linkedin.learning.converter;

import org.springframework.core.convert.converter.Converter;

import com.linkedin.learning.entity.Room;
import com.linkedin.learning.model.Links;
import com.linkedin.learning.model.Self;
import com.linkedin.learning.model.response.ReservableRoomResponse;
import com.linkedin.learning.rest.ResourceConstants;

public class RoomEntityToReservableRoomResponseConverter implements Converter<Room, ReservableRoomResponse> {

	@Override
	public ReservableRoomResponse convert(Room source) {
		ReservableRoomResponse reservationResponse = new ReservableRoomResponse();
		reservationResponse.setRoomNumber(source.getRoomNumber());
		reservationResponse.setPrice(Integer.valueOf(source.getPrice()));
		
		Links links = new Links();
		Self self = new Self();
		
		self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/"+ source.getId());
		links.setSelf(self);
		reservationResponse.setLinks(links);
		
		return reservationResponse;
	}
	
	
}
