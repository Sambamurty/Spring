package com.room.expenses.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "roomslist")
public class RoomsList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roomid")
	private Integer roomId;
	
	@Column(name = "roomname")
	private String roomName;
	
	@Column(name = "countofmembers")
	private Integer countOfMembers;
	
	@OneToMany(mappedBy = "roomsList" , cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<MembersList> member;
	
	@OneToMany(mappedBy = "roomsList" , cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Expenses> expenses;
	
	public RoomsList() {
		super();
	}

	public RoomsList(Integer roomId, String roomName, Integer countOfMembers, List<MembersList> member,
			List<Expenses> expenses) {
		super();
		this.roomId = roomId;
		this.roomName = roomName;
		this.countOfMembers = countOfMembers;
		this.member = member;
		this.expenses = expenses;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Integer getCountOfMembers() {
		return countOfMembers;
	}

	public void setCountOfMembers(Integer countOfMembers) {
		this.countOfMembers = countOfMembers;
	}

	public List<MembersList> getMember() {
		return member;
	}

	public void setMember(List<MembersList> member) {
		this.member = member;
	}

	public List<Expenses> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expenses> expenses) {
		this.expenses = expenses;
	}
	
}
