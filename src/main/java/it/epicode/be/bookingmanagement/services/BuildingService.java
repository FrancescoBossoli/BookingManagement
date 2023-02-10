package it.epicode.be.bookingmanagement.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.epicode.be.bookingmanagement.models.Building;
import it.epicode.be.bookingmanagement.models.City;
import it.epicode.be.bookingmanagement.repositories.BuildingRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BuildingService {

	@Autowired
	private BuildingRepository bR;
	
	@Autowired
	private CityService cS;
	
	//This method saves the City if it's not already present in the Database, then checks if the Building is already listed before saving it.
	public void save(Building b) {
		if (cS.getCityByName(b.getCity().getName()).size() == 0) cS.save(b.getCity());
		City c = cS.getCityByName(b.getCity().getName()).get(0);
		if (findExistingBuilding(b.getName(), b.getAddress(), c.getId()).size() != 0) 
			log.info("The specified Building is already listed in the database");
		else {
			Building b2 = Building.builder().city(c).address(b.getAddress()).name(b.getName()).build();
			bR.save(b2);
			log.info("The Building has been saved in the Database.");
		}
	}
	
	public Optional<Building> getBuildingById(Long id) {
		return bR.findById(id);
	}
	
	public List<Building> findExistingBuilding(String n, String a, Long i) {
		return bR.findExistingBuilding(n, a, i);
	}
	
	
	public List<Building> getBuildingByNameAndAddress(String n, String a) {
		return bR.findBuildingsByNameAndAddress(n, a);
	}
	
	public List<Building> getBuildingByName(String n) {
		return bR.findBuildingByName(n);
	}	
	
	public List<Building> getAllBuildings(){
		return bR.findAll();
	}
	
	public void deleteBuildingById(Long id) {
		bR.deleteById(id);
	}
	
	public void printList(List<Building> list) {
		for (Building l : list)
			log.info(l.toString());
	}
}
