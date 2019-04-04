package com.linkedin.learning.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.learning.converter.ReservationEntityToReservationResponseConverter;
import com.linkedin.learning.converter.ReservationRequestToReservationEntityConverter;
import com.linkedin.learning.entity.Reservation;
import com.linkedin.learning.entity.Room;
import com.linkedin.learning.model.Links;
import com.linkedin.learning.model.Self;
import com.linkedin.learning.model.request.ReservationRequest;
import com.linkedin.learning.model.response.ReservableRoomResponse;
import com.linkedin.learning.model.response.ReservationResponse;
import com.linkedin.learning.repository.PageableRoomRepository;
import com.linkedin.learning.repository.ReservationRepository;
import com.linkedin.learning.repository.RoomRepository;

@RestController
@CrossOrigin
@RequestMapping(path = ResourceConstants.ROOM_RESERVATION_V1)
public class ReservationResource {

	@Autowired
	PageableRoomRepository pageableRoomRepository;

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	ReservationEntityToReservationResponseConverter reservationEntityToReservationResponseConverter;

	@Autowired
	ReservationRequestToReservationEntityConverter reservationRequestToReservationEntityConverter;

	@RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Page<ReservableRoomResponse> getAvailableRooms(
			@RequestParam(value = "checkin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
			@RequestParam(value = "checkout", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
			Pageable pageable) {
		Page<Room> roomEntityList = pageableRoomRepository.findAll(pageable);

		return roomEntityList.map(new Function<Room, ReservableRoomResponse>() {

			@Override
			public ReservableRoomResponse apply(Room source) {
				ReservableRoomResponse reservationResponse = new ReservableRoomResponse();
				reservationResponse.setRoomNumber(source.getRoomNumber());
				reservationResponse.setPrice(Integer.valueOf(source.getPrice()));
				reservationResponse.setId(source.getId());
				Links links = new Links();
				Self self = new Self();
				self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
				links.setSelf(self);
				reservationResponse.setLinks(links);

				return reservationResponse;
			}

		});
	}

	@RequestMapping(path = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Room> getRoomById(@PathVariable Long roomId) {
		Room room = roomRepository.findById(roomId).orElse(new Room());
		return new ResponseEntity<>(room, HttpStatus.OK);

	}

	@RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {

		Reservation reservation = reservationRequestToReservationEntityConverter.apply(reservationRequest);
		Reservation save = reservationRepository.save(reservation);
		Room room = roomRepository.findById(reservationRequest.getRoomId()).orElse(new Room());
		room.addReservationEntity(save);
		roomRepository.save(room);
		reservation.setRoom(room);
		
		
		ReservationResponse reservationResponse = reservationEntityToReservationResponseConverter.apply(reservation);

		return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);

	}

	@RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReservableRoomResponse> updateReservation(
			@RequestBody ReservationRequest reservationRequest) {

		return new ResponseEntity<ReservableRoomResponse>(new ReservableRoomResponse(), HttpStatus.ACCEPTED);

	}

	@RequestMapping(path = "/{reservationId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteReservation(@PathVariable long reservationId) {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(path = "/test")
	public String test() {
		return new String("Hello World");

	}

}
