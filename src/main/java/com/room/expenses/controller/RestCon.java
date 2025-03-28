package com.room.expenses.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.room.expenses.dao.Expenses;
import com.room.expenses.dao.RoomsList;
import com.room.expenses.service.FiltersService;
import com.room.expenses.service.RExpensesService;

@RestController
@RequestMapping("/api")
public class RestCon {

	@Autowired
	private RExpensesService rExpensesService;
	@Autowired
	private FiltersService filtersService;

	// Create Room
	@PostMapping("/createRoom")
	public String createRoom(@RequestBody Map<String, Object> requestBody) {
		try {
			String roomName = (String) requestBody.get("tableName");
			List<String> names = (List<String>) requestBody.get("names");
			rExpensesService.createRoom(roomName, names);
			return "Room created successfully!";
		} catch (IllegalArgumentException e) {
			return "Error: " + e.getMessage();
		}
	}

	// Get List of Rooms
	@GetMapping("/getListOfRooms")
	public List<RoomsList> getListOfRooms() {
		return rExpensesService.getListOfRooms();
	}

	// Get Room Details
	@PostMapping("/getListOfRooms/roomDetails")
	public ResponseEntity<Map<String, Object>> getRoomDetails(
	        @RequestParam String roomName,
	        @RequestParam(required = false) String name,
	        @RequestParam(required = false) LocalDateTime fromDate,
	        @RequestParam(required = false) LocalDateTime toDate,
	        @RequestParam(required = false) Double floorPrice,
	        @RequestParam(required = false) Double ceilPrice) {

	    try {
	        // Retrieve names by room
	        List<String> nameList = rExpensesService.getNamesByRoom(roomName);

	        // Filter expenses by conditions
	        List<Expenses> expensesByRoom;
	        if (name != null && !name.isEmpty()) {
	            Integer roomId = rExpensesService.getRoomIdByRoomName(roomName);
	            expensesByRoom = filtersService.getByName(name, roomId);
	        } else if (fromDate != null && toDate != null) {
	            expensesByRoom = filtersService.getBetweenDate(fromDate, toDate, roomName);
	        } else if (floorPrice != null || ceilPrice != null) {
	            expensesByRoom = filtersService.getBetweenPrice(floorPrice, ceilPrice, roomName);
	        } else {
	            expensesByRoom = rExpensesService.getExpensesByRoom(roomName);
	        }
	        Map<Integer,String> namesById = rExpensesService.getpersonIdAndpersonName(roomName);
	        
	        
	        // Create a response map
	        Map<String, Object> response = new HashMap<>();
	        response.put("namesList", nameList);
	        response.put("roomName", roomName);
	        response.put("expensesByRoom", expensesByRoom);
	        response.put("namesByIds", namesById);
	        // Return the response as JSON
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        // Return error response as Map<String, Object>
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("error", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}


	// Add Expenses
	@GetMapping("/getListOfRooms/roomDetails/addExpenses")
	public ResponseEntity<String> addExpenses(
	        @RequestParam String personName,
	        @RequestParam String items,
	        @RequestParam Double cost,
	        @RequestParam String roomName) {
	    rExpensesService.addExpensesByUser(personName, items, cost, roomName);
	    return ResponseEntity.ok("Expense added successfully!");
	}
	

	// Get Monthly Payments
	@GetMapping("/getListOfRooms/roomDetails/getMonthlyToPay")
	public ResponseEntity<Map<String, Double>> getMonthlyToPay(@RequestParam String roomName) {
		return ResponseEntity.ok(rExpensesService.getMonthlyToPay(roomName));
	}

	// Delete Room
	@PostMapping("/deleteRoom")
	public ResponseEntity<String> deleteRoom(@RequestParam("name") String roomName) {
		rExpensesService.deleteRoom(roomName);
		return ResponseEntity.ok("Room deleted successfully!");
	}
	
	@PostMapping("/deleteRecord")
    public ResponseEntity<Map<String, Object>> deleteRecord(
            @RequestParam String personName,
            @RequestParam String date,
            @RequestParam Integer roomId) {
        try {
            // Call the service to delete the record
            String roomName = rExpensesService.deleteRecord(personName, date, roomId);

            // Create a response map
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Record deleted successfully");
            response.put("roomName", roomName);

            // Return response
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle exceptions and return error response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to delete record");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
	
	@GetMapping("/getByPerson")
	public ResponseEntity<Map<String, String>> getByPerson(@RequestParam String name,@RequestParam String roomName)
	{
		Map<String, String> response = new HashMap<>();
		response.put("roomName", roomName);
		response.put("name", name);
		return ResponseEntity.ok(response);	
	}
}
