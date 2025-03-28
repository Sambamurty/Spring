package com.room.expenses.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room.expenses.dao.Expenses;
import com.room.expenses.dao.MembersList;
import com.room.expenses.dao.RoomsList;
import com.room.expenses.repository.ExpensesRepo;
import com.room.expenses.repository.MemberListRepo;
import com.room.expenses.repository.RExpensesRepo;

@Service
public class FiltersService {
	
	@Autowired
	private MemberListRepo memberListRepo;
	@Autowired
	private ExpensesRepo expensesRepo;
	@Autowired
	private RExpensesRepo rExpensesRepo;
	

	public List<Expenses> getByName(String name,Integer roomId) {
		MembersList byPersonNameAndRoomId = memberListRepo.findByPersonNameAndRoomId(name, roomId);
		List<Expenses> expensesByName = 
				expensesRepo.findByPersonId(byPersonNameAndRoomId.getPersonId());
		return expensesByName;
	}

	public String getRoomName(Integer roomId) {
		RoomsList byRoomId = rExpensesRepo.findByRoomId(roomId);
		return byRoomId.getRoomName();
	}

	public List<Expenses> getBetweenDate(LocalDateTime fromDate, LocalDateTime toDate, String roomName) {
		Integer roomId = getRoomIdByRoomName(roomName);
		List<Expenses> expensesBetweenDate = 
				expensesRepo.findBetweenDateAndRoomId(fromDate, toDate, roomId);
				
		return expensesBetweenDate;
	}
	private Integer getRoomIdByRoomName(String roomName) {
		RoomsList byRoomName = rExpensesRepo.findByRoomName(roomName);
		return byRoomName.getRoomId();
	}

	public List<Expenses> getBetweenPrice(Double floorPrice, Double ceilPrice, String roomName) {
		if(floorPrice == null)
    	{
    		floorPrice = (double) 0;
    	}
    	else if(ceilPrice == null)
    	{
    		ceilPrice = (double) 0;
    	}
		Integer roomId = getRoomIdByRoomName(roomName);
		List<Expenses> expensesBetweenPrice =
				expensesRepo.findBetweenPriceAndName(floorPrice,ceilPrice,roomId);
		return expensesBetweenPrice;
	}
	
}
