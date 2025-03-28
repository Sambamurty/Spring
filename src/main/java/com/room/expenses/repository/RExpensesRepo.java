package com.room.expenses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.room.expenses.dao.RoomsList;

@Repository
public interface RExpensesRepo extends JpaRepository<RoomsList, Integer>{

	RoomsList findByRoomName(String roomName);
	
	RoomsList findByRoomId(Integer roomId);

	void deleteByRoomId(Integer roomId);

}
