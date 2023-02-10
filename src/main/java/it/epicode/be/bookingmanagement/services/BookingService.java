package it.epicode.be.bookingmanagement.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.be.bookingmanagement.models.Booking;
import it.epicode.be.bookingmanagement.models.Building;
import it.epicode.be.bookingmanagement.models.User;
import it.epicode.be.bookingmanagement.models.WorkStation;
import it.epicode.be.bookingmanagement.repositories.BookingRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookingService {
	
	@Autowired
	private BookingRepository bR;
	
	@Autowired
	private UserService uS;
	
	@Autowired
	private WorkStationService wS;	
	
	@Autowired
	private BuildingService bS;
	
	//This method checks if the WorkStation related to the Booking or its Building already exist in the Database. Then it checks if the location is
	//already full or if the User already has active bookings for the specified Date. After passing all the checks, the Booking is saved in the DB.
	public void save(Booking b) {
		User u = b.getUser();
		WorkStation w= b.getWorkstation();
		if (uS.getExistingUser(u.getUsername(), u.getFullname(), u.getEmail()).size() == 0) uS.save(u);		
		u = uS.getExistingUser(u.getUsername(), u.getFullname(),u.getEmail()).get(0);
		List<Building> bq = bS.getBuildingByNameAndAddress(w.getBuilding().getName(), w.getBuilding().getAddress());
		if (bq.size() == 0) log.info("The selected WorkStation and its Building couldn't be found.");
		else {
			Building bg = bq.get(0);			
			List<WorkStation> ws = wS.getExistingWorkStation(w.getDescription(), w.getType(), w.getCapacity(), bg.getId());
			if (ws.size() == 0) log.info("The selected WorkStation wasn't found.");
			else {
				w = ws.get(0);
				if (countBookings(b.getDate(), w.getId()) == w.getCapacity()) log.info("The selected WorkStation isn't available for the selected date.");
				else {
					if (findBookingsByDateAndUserId(b.getDate(), u.getId()).size() != 0) 
						log.info("The selected User already has a Booking listed for the specified date.");
					else {
						Booking b2 = Booking.builder().user(u).workstation(w).date(b.getDate()).build();
						bR.save(b2);
						log.info("The Booking has been saved in the Database.");
					}
				}				
			}
		}		
	}
	
	public int countBookings(LocalDate d, Long id) {
		return bR.countBookingsByDateAndWorkStation(d, id);
	}	
	
	public List<Booking> findBookingsByDateAndUserId(LocalDate d, Long id) {
		return bR.findBookingsByDateAndUser(d, id);
	}
	
	public Optional<Booking> getBookingById(Long id) {
		return bR.findById(id);
	}
	
	public List<Booking> getAllBookings(){
		return bR.findAll();
	}
	
	public void deleteBookingById(Long id) {
		bR.deleteById(id);
	}
	
	public void printList(List<Booking> list) {
		for (Booking l : list)
			log.info(l.toString());
	}

}
