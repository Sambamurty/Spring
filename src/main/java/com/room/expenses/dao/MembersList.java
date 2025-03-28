package com.room.expenses.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "memberslist")
public class MembersList {
	
	@Id
	@Column(unique = true,name = "personid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer personId;
	
	@Column(name = "roomid", insertable = false, updatable = false)
	private Integer roomId;
	
	@Column(name = "personname")
	private String personName;
	
	@ManyToOne
    @JoinColumn(name = "roomid", referencedColumnName = "roomid")
	@JsonIgnore
    private RoomsList roomsList;
	
	@OneToMany(mappedBy = "membersList", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
    private List<Expenses> expenses;

	public MembersList() {
		super();
	}

	public MembersList(Integer personId, Integer roomId, String personName, RoomsList roomsList,
			List<Expenses> expenses) {
		super();
		this.personId = personId;
		this.roomId = roomId;
		this.personName = personName;
		this.roomsList = roomsList;
		this.expenses = expenses;
	}

	public List<Expenses> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expenses> expenses) {
		this.expenses = expenses;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public RoomsList getRoomsList() {
		return roomsList;
	}

	public void setRoomsList(RoomsList roomsList) {
		this.roomsList = roomsList;
	}
	
	
}
