package com.sample.couchbase.spring.mca.web;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample.couchbase.spring.mca.domain.Airport;
import com.sample.couchbase.spring.mca.service.AirportService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/airport")
@Slf4j
public class AirportMvcController {
	
	@Autowired
	AirportService service;

	@RequestMapping
	public String getAllAirports(Model model) {
		log.info("Web - getAllAirports {}", model);		
		//List<Airport> list = service.findAllQuery();
		List<Airport> list = service.findAll();
		log.info("Web - getAllAirports {}", list.size());
		model.addAttribute("airports", list);
		return "list-airports";
	}

	@RequestMapping(path = {"/edit", "/edit/{id}"})
	public String editAirportById(Model model, @PathVariable("id") Optional<String> id) throws RecordNotFoundException {
		if (id.isPresent()) {
			Optional<Airport> entity = service.findById(id.get());
			log.info("editAirportById {}", entity.get());
			model.addAttribute("airport", entity.get());
		} else {
			model.addAttribute("airport", new Airport());
		}
		return "add-edit-airport";
	}
	
	@RequestMapping(path = "/delete/{id}")
	public String deleteAirportById(Model model, @PathVariable("id") String id) 
							throws RecordNotFoundException  {
		log.info("Web - deleteAirportById START-------id:{}", id);
		service.deleteById(id);
		return "redirect:/airport";
	}

	@RequestMapping(path = "/createAirport", method = RequestMethod.POST)
	public String createOrUpdateAirport(Airport airport, @RequestParam(value="action", required=true) String action) 
	{
		log.info("Creating/Updating action: {}, airport: {}", action, airport);
		String redirect = null;
		switch(action) {
	        case " Submit ":
	        	service.create(airport);
	        	redirect = "redirect:/airport";
	            break;
	        case " Cancel ":
	        	log.info("Cancel - action: {}", action);
	        	redirect = "redirect:/airport";
	            break;
	        default:
	        	redirect = "redirect:/airport";
	            break;
		 }
		 return redirect;
	}
	
	
}
