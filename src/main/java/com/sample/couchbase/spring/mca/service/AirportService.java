package com.sample.couchbase.spring.mca.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sample.couchbase.spring.mca.domain.Airport;
import com.sample.couchbase.spring.mca.repository.AirportN1QLRepository;

@Service
public class AirportService {

	@Autowired
	@Qualifier("airportN1QLRepository")
	protected AirportN1QLRepository airportN1QLRepository;
	
	public Airport create(Airport airport) {
		return airportN1QLRepository.save(airport);
	}
	
	public Airport update(Airport airport) {
		return airportN1QLRepository.save(airport);
	}	

	public Optional<Airport> findById(String id) {
		return airportN1QLRepository.findById(id);
	}

	public void deleteById(String id) {
		airportN1QLRepository.deleteById(id);
	}		

	/*
	 * Error -  com.couchbase.client.java.error.ViewDoesNotExistException: View airport/all does not exist.] with root cause
	 */
	public void deleteAll() {
		airportN1QLRepository.deleteAll();
	}
	
	public int getAllCount() {
		return airportN1QLRepository.getAllCount();
	}
	
	public List<Airport> findAllQuery() {
		
		List<Airport> result = (List<Airport>) airportN1QLRepository.findAllQuery();
		if(result.size() > 0) {
			return result;
		} else {
			return new ArrayList<Airport>();
		}
		
	}

	public List<Airport> findAll() {
		
		List<Airport> airports = new ArrayList<>();
		Airport airport;
		List<String> properties = new ArrayList<String>();
		properties.add("name");
		//Iterator<Airport> it = airportN1QLRepository.findAll(sort).iterator();
		Iterator<Airport> it = airportN1QLRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).iterator();
		//Iterator<Airport> it = airportN1QLRepository.findAll().iterator();
        while (it.hasNext()) {
        	airport = it.next();        	
        	airports.add(airport);
        }		
		
        return airports;
	}	
}
