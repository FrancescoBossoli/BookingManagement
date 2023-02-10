package it.epicode.be.bookingmanagement.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.epicode.be.bookingmanagement.models.Building;
import it.epicode.be.bookingmanagement.models.RoomType;
import it.epicode.be.bookingmanagement.models.WorkStation;
import it.epicode.be.bookingmanagement.repositories.WorkStationRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WorkStationService {
	
	@Autowired
	private WorkStationRepository wR;
	
	@Autowired
	private BuildingService bS;
	
	//This method saves the Building if it's not already present in the Database, then checks if the WorkStation is already listed before saving it.
	public void save(WorkStation w) {
		if (bS.getBuildingByNameAndAddress(w.getBuilding().getName(), w.getBuilding().getAddress()).size() == 0) bS.save(w.getBuilding());
		Building b = bS.getBuildingByNameAndAddress(w.getBuilding().getName(), w.getBuilding().getAddress()).get(0);
		if (getExistingWorkStation(w.getDescription(), w.getType(), w.getCapacity(), b.getId()).size() != 0) 
			log.info("The specified WorkStation is already listed in the database");
		else {
			WorkStation w2 = WorkStation.builder().description(w.getDescription()).type(w.getType()).capacity(w.getCapacity()).building(b).build();
			wR.save(w2);
			log.info("The WorkStation has been saved in the Database.");
		}		
	}
	
	public Optional<WorkStation> getWorkStationById(Long id) {
		return wR.findById(id);
	}
	
	public List<WorkStation> getAllWorkStations(){
		return wR.findAll();
	}
	
	public List<WorkStation> getExistingWorkStation(String d, RoomType t, int c, Long i) {
		return wR.findExistingWorkStation(d, String.valueOf(t), c, i);
	}
	
	public List<WorkStation> getWorkStationByTypeAndCityId(RoomType t, Long i) {
	return wR.findWorkStationByTypeAndCityId(String.valueOf(t), i);
	}
	
	public void deleteWorkStationById(Long id) {
		wR.deleteById(id);
	}
	
	public void printList(List<WorkStation> list) {
		for (WorkStation l : list)
			log.info(l.toString());
	}

}
