package com.sample.couchbase.spring.mca.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.couchbase.spring.mca.domain.Airport;
import com.sample.couchbase.spring.mca.service.AirportService;

@RestController
@RequestMapping({"/api"})
public class AirportRestController {

	private static final Logger logger = LoggerFactory.getLogger(AirportRestController.class);
	
	@Autowired	
	private AirportService airportService;

	  /*
	  @Autowired
	  private CouchbaseTemplate template;

	  @Autowired
	  private AirportRepository repository;

	  @GetMapping("/airports/{id}")
	  public ResponseEntity<Airport> read(@PathVariable String id) {
	    Airport airport =  template.findById("airport_" + id, Airport.class);
	    return new ResponseEntity<>(airport, HttpStatus.OK);
	  }

	  @GetMapping("/airports")
	  public ResponseEntity<Iterable<Airport>> readAll() {
	    return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	  }
	  */
	
	@GetMapping("/airports/{id}")
	public ResponseEntity<Airport> findAirportById(@PathVariable("id") String id) {
		return airportService.findById(id)
		         .map(record -> ResponseEntity.ok().body(record))
		         .orElse(ResponseEntity.notFound().build());		
		/*
		Optional<Airport> airport = airportService.findById(id);
		return new ResponseEntity<>(airport.get(), HttpStatus.OK);
		*/
	}
	
	@PostMapping("/airports")
	public Airport createAirport(@RequestBody Airport airport) {
		return airportService.create(airport);
	}	

	@PutMapping("/airports/{id}")
	public ResponseEntity<Airport> update(@PathVariable String id, @Valid @RequestBody Airport product) {
        if (!airportService.findById(id).isPresent()) {
        	logger.error("{} is not existing in db", id);
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(airportService.update(product));
	}	
	
	@DeleteMapping("/airports/{id}")
	public ResponseEntity<?> deleteAirport(@PathVariable String id) {
		if (!airportService.findById(id).isPresent()) {
            logger.error("{} is not existing in db", id);
            ResponseEntity.badRequest().build();
        }
		
		airportService.deleteById(id);
		return ResponseEntity.ok().build();
	}	

	@GetMapping("/airports")
	public  ResponseEntity<List<Airport>> findAll() {
		//return new ResponseEntity<>(airportService.findAllAirports(), HttpStatus.OK);
		return ResponseEntity.ok(airportService.findAll());
	}
	
	@DeleteMapping("/airports")
	public void deleteAllAirports() {
		airportService.deleteAll();
	}		

}
