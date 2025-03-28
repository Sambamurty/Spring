package com.room.expenses.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.room.expenses.dao.Expenses;
import com.room.expenses.dao.MembersList;

public interface ExpensesRepo extends JpaRepository<Expenses, Integer> {

	List<Expenses> findByRoomId(Integer roomId);

	List<Expenses> findByPersonId(Integer byPersonNameAndRoomId);

	@Query("SELECT e FROM expenses e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month AND roomId = :roomId")
	List<Expenses> findByYearAndMonthAndRoomId(@Param("year") int year, @Param("month") int month,@Param("roomId") int roomId);

	@Query("SELECT SUM(e.cost) FROM expenses e WHERE personId = :personId")
	Double getSumOfCostsByPersonId(@Param("personId") Integer personId);

	void deleteAllByRoomId(Integer roomId);

	 @Query("SELECT e FROM expenses e WHERE e.date BETWEEN :fromDate AND :toDate AND e.roomId = :roomId")
    List<Expenses> findBetweenDateAndRoomId(@Param("fromDate") LocalDateTime fromDate, 
                                            @Param("toDate") LocalDateTime toDate, 
                                            @Param("roomId") Integer roomId);
	 
	 
	 @Query("SELECT e FROM expenses e WHERE e.cost BETWEEN :floorPrice AND :ceilPrice and e.roomId = :roomId")
	List<Expenses> findBetweenPriceAndName(@Param("floorPrice") Double floorPrice, 
											@Param("ceilPrice") Double ceilPrice,
											@Param("roomId") Integer roomId);

	void deleteByPersonIdAndRoomIdAndDate(Integer personId, Integer roomId, LocalDateTime date);

}
