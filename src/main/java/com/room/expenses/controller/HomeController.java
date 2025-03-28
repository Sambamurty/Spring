package com.room.expenses.controller;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.room.expenses.dao.Expenses;
import com.room.expenses.dao.MembersList;
import com.room.expenses.dao.RoomsList;
import com.room.expenses.service.FiltersService;
import com.room.expenses.service.RExpensesService;

@Controller
public class HomeController {

    @Autowired
    private RExpensesService rExpensesService;
    @Autowired
    private FiltersService filtersService;

    @GetMapping("/createRoom")
    public String createRoom() {
        return "createRoom";
    }

    @PostMapping("/createRoom")
    public String createRoomPost(@RequestParam("tableName") String roomName, @RequestParam List<String> names, Model model) {
    	try {
    		rExpensesService.createRoom(roomName, names);
    		return "redirect:/getListOfRooms";
    	}
    	catch(IllegalArgumentException e)
    	{
    		model.addAttribute("error", e.getMessage());
    		return "redirect:/createRoom";
    	}
    }
    
    @GetMapping("/getListOfRooms")
    public String getListOfRooms(Model model)
    {
    	List<RoomsList> listOfRooms=rExpensesService.getListOfRooms();
    	model.addAttribute("listOfRooms", listOfRooms);
    	return "displayListOfRooms";
    }
    
    @GetMapping("/getListOfRooms/roomDetails")
    public String getRoomDetails(@RequestParam String roomName,@RequestParam(required = false) String name,
    		@RequestParam(required = false) LocalDateTime fromDate,
    		@RequestParam(required = false) LocalDateTime toDate,
    		@RequestParam(required = false) Double floorPrice,
    		@RequestParam(required = false) Double ceilPrice,
    		Model model) {
    	List<String> nameList = rExpensesService.getNamesByRoom(roomName);
        List<Expenses> expensesByRoom;
        if (name != null && !name.isEmpty()) {
            Integer roomId = rExpensesService.getRoomIdByRoomName(roomName);
            expensesByRoom = filtersService.getByName(name, roomId); // Filtered data
        } 
        else if(fromDate !=null && toDate != null) {
        	expensesByRoom = filtersService.getBetweenDate(fromDate,toDate, roomName);
        }
        else if(floorPrice != null || ceilPrice !=null) {
        	expensesByRoom = filtersService.getBetweenPrice(floorPrice,ceilPrice,roomName);
        }
        else {
            expensesByRoom = rExpensesService.getExpensesByRoom(roomName); // All data
        }
        model.addAttribute("namesList", nameList);
        model.addAttribute("roomName", roomName);
        model.addAttribute("expensesByRoom", expensesByRoom);
        return "roomDetails";
    }
     
    @PostMapping("/getListOfRooms/roomDetails/addExpenses")
    public String addExpensesPost(@RequestParam("username") String personName,@RequestParam("itemName") String items,
    							  @RequestParam Double cost,  @RequestParam String roomName)
    {
    	rExpensesService.addExpensesByUser(personName,items,cost,roomName);
    	return "redirect:/getListOfRooms/roomDetails?roomName="+roomName;
    }
    
    @GetMapping("/getListOfRooms/roomDetails/getMonthlyToPay")
    public String getMonthlyToPay(Model model,@RequestParam String roomName)
    {
    	Map<String,Double> pay= rExpensesService.getMonthlyToPay(roomName);
    	model.addAttribute("paymentsList", pay);
    	return "getMonthlyToPay";
    }
    
    @PostMapping("/deleteRoom")
	public String deleteRoom(@RequestParam("name") String roomName)
	{
		rExpensesService.deleteRoom(roomName);
		return "redirect:/getListOfRooms";
	}
    
//    @PostMapping("/editRecord")
//    public String editRecord(@RequestParam("name") String roomName)
//    {
//    	return "redirect:/getListOfRooms/roomDetails?roomName="+roomName;
//    }
    
    @PostMapping("/deleteRecord")
    public String deleteRecord(@RequestParam String personName,
    		@RequestParam String date,
    		@RequestParam Integer roomId)
    {
    	String roomName = rExpensesService.deleteRecord(personName, date, roomId);
    	return "redirect:/getListOfRooms/roomDetails?roomName="+roomName;
    }
    
    

   
}

