package com.room.expenses.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.room.expenses.dao.MembersList;

@Repository
public interface MemberListRepo extends JpaRepository<MembersList, Integer>{
	
	List<MembersList> findByRoomId(Integer roomId);

	MembersList findByPersonNameAndRoomId(String personName, Integer roomId);

	MembersList findByPersonId(Integer i);
	
	@Query("SELECT p.id FROM memberslist p WHERE p.roomId = :roomId")
    List<Integer> findPersonIdsByRoomId(@Param("roomId") Integer roomId);

	void deleteAllByRoomId(Integer roomId);
	
	@Query("SELECT m.personId FROM memberslist m WHERE m.roomId = :roomId AND m.personName = :personName")
	Integer findByRoomIdAndPersonName(@Param("roomId") Integer roomId,@Param("personName") String personName);


}
