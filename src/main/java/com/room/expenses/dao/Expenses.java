package com.room.expenses.dao;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "expenses")
public class Expenses {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enteryId;

	@Column(name = "personid", insertable = false, updatable = false)
	private Integer personId;
	
	@Column(name = "roomid", insertable = false, updatable = false)
	private Integer roomId;

	private String items;

	private Double cost;

	private LocalDateTime date;
	
	@ManyToOne
	@JoinColumn(name = "personid", referencedColumnName = "personid")
	@JsonIgnore
	private MembersList membersList;
	
	@ManyToOne
	@JoinColumn(name = "roomid", referencedColumnName = "roomid")
	@JsonIgnore
	private RoomsList roomsList;

	public Expenses() {
		super();
	}


	public Expenses(Integer personId, Integer roomId, String items, Double cost, LocalDateTime date,
			MembersList membersList, RoomsList roomsList) {
		super();
		this.personId = personId;
		this.roomId = roomId;
		this.items = items;
		this.cost = cost;
		this.date = date;
		this.membersList = membersList;
		this.roomsList = roomsList;
	}


	public Integer getEnteryId() {
		return enteryId;
	}


	public void setEnteryId(Integer enteryId) {
		this.enteryId = enteryId;
	}


	public Integer getPersonId() {
		return personId;
	}


	public void setPersonId(Integer personId) {
		this.personId = personId;
	}


	public Integer getRoomId() {
		return roomId;
	}


	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}


	public String getItems() {
		return items;
	}


	public void setItems(String items) {
		this.items = items;
	}


	public Double getCost() {
		return cost;
	}


	public void setCost(Double cost) {
		this.cost = cost;
	}


	public LocalDateTime getDate() {
		return date;
	}


	public void setDate(LocalDateTime date) {
		this.date = date;
	}


	public MembersList getMembersList() {
		return membersList;
	}


	public void setMembersList(MembersList membersList) {
		this.membersList = membersList;
	}


	public RoomsList getRoomsList() {
		return roomsList;
	}


	public void setRoomsList(RoomsList roomsList) {
		this.roomsList = roomsList;
	}


	@Override
	public String toString() {
		return "Expenses [enteryId=" + enteryId + ", personId=" + personId + ", roomId=" + roomId + ", items=" + items
				+ ", cost=" + cost + ", date=" + date + ", membersList=" + membersList + ", roomsList=" + roomsList
				+ "]";
	}
	
	
}
