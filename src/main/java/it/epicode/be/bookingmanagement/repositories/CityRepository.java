package it.epicode.be.bookingmanagement.repositories;

import it.epicode.be.bookingmanagement.models.City;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM cities WHERE name = :n")
	List<City> findCityByName(@Param("n") String n);
	
}
