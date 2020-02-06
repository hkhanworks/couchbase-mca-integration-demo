package com.sample.couchbase.spring.mca.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.sample.couchbase.spring.mca.domain.Airport;

@Repository
//public interface AirportN1QLRepository extends CouchbaseRepository<Airport, String> {
public interface AirportN1QLRepository extends CouchbasePagingAndSortingRepository <Airport, String>{
		
	public static final String QUERY_FINDALL_AIRPORTS = "#{#n1ql.selectEntity} WHERE #{#n1ql.filter}";
	public static final String QUERY_COUNT_AIRPORTS = " SELECT count(*) AS totalcount FROM #{#n1ql.bucket} WHERE #{#n1ql.filter}";
	 
	@Query(QUERY_COUNT_AIRPORTS)
	public int getAllCount();
	
	@Query(QUERY_FINDALL_AIRPORTS)
	public List<Airport> findAllQuery();
	
	@Override
	//@Query("#{#n1ql.selectEntity} where type = \"airport\"")
	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter}")
	Iterable<Airport> findAll();
}
