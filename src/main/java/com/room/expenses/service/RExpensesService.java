package com.room.expenses.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.room.expenses.controller.FiltersController;
import com.room.expenses.dao.Expenses;
import com.room.expenses.dao.MembersList;
import com.room.expenses.dao.RoomsList;
import com.room.expenses.repository.ExpensesRepo;
import com.room.expenses.repository.MemberListRepo;
import com.room.expenses.repository.RExpensesRepo;

import jakarta.transaction.Transactional;

@Service
public class RExpensesService {

    private final FiltersController filtersController;

	@Autowired
	private RExpensesRepo rExpensesRepo;
	@Autowired
	private MemberListRepo memberListRepo;
	@Autowired
	private ExpensesRepo expensesRepo;
	
	private Integer roomId;

    RExpensesService(FiltersController filtersController) {
        this.filtersController = filtersController;
    }

	@Transactional
	public void createRoom(String roomName, List<String> names) {
		// Create and save room
		if (rExpensesRepo.findByRoomName(roomName) == null) {
			RoomsList room = new RoomsList();
			room.setRoomName(roomName);
			room.setCountOfMembers(names.size());
			rExpensesRepo.save(room);

			// Create and save members
			List<MembersList> members = new ArrayList<>();
			for (String name : names) {
				MembersList member = new MembersList();
				member.setPersonName(name);
				member.setRoomsList(room); // Set the relationship
				members.add(member);
			}
			memberListRepo.saveAll(members);
		}
		else {
			throw new IllegalArgumentException("Room already exists with room name: " + roomName);
		}
	}
	
	public List<RoomsList> getListOfRooms() {
		return rExpensesRepo.findAll();
	}

	public List<String> getNamesByRoom(String roomName) {
		RoomsList room = rExpensesRepo.findByRoomName(roomName);
		List<MembersList> memberList = memberListRepo.findByRoomId(room.getRoomId());
		return memberList.stream().map(MembersList::getPersonName).collect(Collectors.toList());
	}

	public void addExpensesByUser(String personName, String items, 
					Double cost, String roomName) {
		
		RoomsList byRoomName = rExpensesRepo.findByRoomName(roomName);
		MembersList details = memberListRepo.findByPersonNameAndRoomId(personName,byRoomName.getRoomId());
		LocalDateTime ldt = LocalDateTime.now();
		if(details != null) {
			Expenses exp = new Expenses(details.getPersonId(),
										details.getRoomId(),
										items,
										cost,
										ldt,
										details,
										details.getRoomsList());
			expensesRepo.save(exp);
		}
		else {
			throw new IllegalArgumentException("Member with name " + personName + 
					" not found.");
		}
		
	}

	public List<Expenses> getExpensesByRoom(String roomName) {
		RoomsList byRoomName = rExpensesRepo.findByRoomName(roomName);
		List<Expenses> byRoomId = expensesRepo.findByRoomId(byRoomName.getRoomId());
		if(byRoomId.size() != 0)
			return byRoomId;
		return null;
	}

	public Integer getRoomIdByRoomName(String roomName) {
		RoomsList byRoomName = rExpensesRepo.findByRoomName(roomName);
		return byRoomName.getRoomId();
	}

	public Map<String, Double> getMonthlyToPay(String roomName) {
		int currentMonth = LocalDateTime.now().getMonth().getValue();
		int currentYear = LocalDateTime.now().getYear();
		roomId = getRoomIdByRoomName(roomName);
		List<Expenses> totalList = expensesRepo.findByYearAndMonthAndRoomId(currentYear,currentMonth,roomId);
		List<Integer> listOfPersonIds = memberListRepo.findPersonIdsByRoomId(roomId);
					
		return getMonthlyToPay(totalList,listOfPersonIds);
	}

	private Map<String, Double> getMonthlyToPay(List<Expenses> totalList, List<Integer> listOfPersonIds) {
		Map<String, Double> res = new LinkedHashMap<String, Double>();
		for(Integer i=0;i<listOfPersonIds.size()-1;i++) {
			Double firstOne = sumOfExpensesByPersonId(totalList,listOfPersonIds.get(i))/listOfPersonIds.size();
			String firstOneName = memberListRepo.findByPersonId(listOfPersonIds.get(i)).getPersonName();
			for(Integer j=i+1;j<listOfPersonIds.size();j++)
			{
				Double secondOne = sumOfExpensesByPersonId(totalList,listOfPersonIds.get(j))/listOfPersonIds.size();
				String secondOneName = memberListRepo.findByPersonId(listOfPersonIds.get(j)).getPersonName();
				if(firstOne > secondOne)
				{
					String toAdd = secondOneName+" to pay "+firstOneName;
					Double amountToPay = firstOne - secondOne;
					res.put(toAdd, amountToPay);
				}
				else if(firstOne < secondOne)
				{
					String toAdd = firstOneName+" to pay "+secondOneName;
					Double amountToPay = secondOne - firstOne;
					res.put(toAdd, amountToPay);
				}
			}
		}
		return res;
	}

	private Double sumOfExpensesByPersonId(List<Expenses> totalList, Integer personId) {
	    return totalList
	            .stream()
	            .filter(e -> e.getPersonId().equals(personId)) // Use .equals() for Integer comparison
	            .mapToDouble(Expenses::getCost) // Convert to a stream of Double values
	            .sum(); // Sum all the Double values
	}
	
	@Transactional
	public void deleteRoom(String roomName) {
		roomId = rExpensesRepo.findByRoomName(roomName).getRoomId();
		expensesRepo.deleteAllByRoomId(roomId);
		memberListRepo.deleteAllByRoomId(roomId);
		rExpensesRepo.deleteByRoomId(roomId);
	}
	
	@Transactional
	public String deleteRecord(String personName, String date, Integer roomId) {
		Integer personId = memberListRepo.findByRoomIdAndPersonName(roomId,personName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
		expensesRepo.deleteByPersonIdAndRoomIdAndDate(personId,roomId,localDateTime);
		RoomsList roomList = rExpensesRepo.findByRoomId(roomId);
		return roomList.getRoomName();
	}

	public Map<Integer, String> getpersonIdAndpersonName(String roomName) {
		Integer roomId = getRoomIdByRoomName(roomName);
		List<MembersList> list = memberListRepo.findByRoomId(roomId);
		Map<Integer, String> res = new HashMap<>();
		for(MembersList l:list)
		{
			res.put(l.getPersonId(), l.getPersonName());
		}
		return res;
	}


	
}
