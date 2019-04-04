package com.linkedin.learning.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Room")
public class Room {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private Integer roomNumber;
	
	@NotNull
	private String price;

	@JsonIgnoreProperties("room")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Reservation> reservationEntityList;
		
	public Room() {
		super();
	}

	public Room(@NotNull Integer roomNumber, @NotNull String price) {
		super();
		this.roomNumber = roomNumber;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<Reservation> getReservationEntityList() {
		return reservationEntityList;
	}

	public void setReservationEntityList(List<Reservation> reservationEntityList) {
		this.reservationEntityList = reservationEntityList;
	}

	public void addReservationEntity(Reservation reservation) {
		if(reservationEntityList == null) {
			reservationEntityList = new ArrayList<>();
			reservationEntityList.add(reservation);
		}else {
			reservationEntityList.add(reservation);
		}
	}
	
	
}
