package com.room.expenses.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.room.expenses.service.FiltersService;

@Controller
@RequestMapping("/getListOfRooms/roomDetails")
public class FiltersController {
	
	@Autowired
	private FiltersService filtersService;
	
	@GetMapping("/getByPerson")
	public String getByPerson(@RequestParam String name,@RequestParam String roomName)
	{
		return "redirect:/getListOfRooms/roomDetails?roomName="
				+roomName+"&name="+name;	
	}
	
	@GetMapping("/getBetweenDates")
	public String getBetweenDates(@RequestParam String fromDate,@RequestParam String toDate,@RequestParam String roomName)
	{
		LocalDate fDate = LocalDate.parse(fromDate);
		LocalDate tDate = LocalDate.parse(toDate);
		LocalDateTime fromDateTime = fDate.atStartOfDay();
		LocalDateTime toDateTime = tDate.atStartOfDay();
		return "redirect:/getListOfRooms/roomDetails?roomName="+
				roomName+"&fromDate="+fromDateTime+"&toDate="+toDateTime;
	}
	
	@GetMapping("/getBetweenPrice")
	public String getBetweenPrice(@RequestParam Double floorPrice,@RequestParam Double ceilPrice,@RequestParam String roomName)
	{
		return "redirect:/getListOfRooms/roomDetails?roomName="+
				roomName+"&floorPrice="+floorPrice+"&ceilPrice="+ceilPrice;
	}
}
