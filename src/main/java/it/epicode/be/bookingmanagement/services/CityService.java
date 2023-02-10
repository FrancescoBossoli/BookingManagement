package it.epicode.be.bookingmanagement.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.epicode.be.bookingmanagement.models.City;
import it.epicode.be.bookingmanagement.repositories.CityRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CityService {

	@Autowired
	private CityRepository cR;
	
	//This method saves a City in the database after checking if its name is already there
	public void save(City c) {
		if (getCityByName(c.getName()).size() == 0) {
			cR.save(c);
			log.info("The City has been saved in the Database.");
		}
		else log.info("The city you were trying to save is already present in the db");
	}
	
	public Optional<City> getCityById(Long id) {
		return cR.findById(id);
	}
	
	public List<City> getCityByName(String n) {
		return cR.findCityByName(n);
	}
		
	public List<City> getAllCities(){
		return cR.findAll();
	}
	
	public void deleteCityById(Long id) {
		cR.deleteById(id);
	}
	
	public void create(City c) {
		cR.save(c);
	}
	
	public void printList(List<City> list) {
		for (City l : list)
			log.info(l.toString());
	}
}
