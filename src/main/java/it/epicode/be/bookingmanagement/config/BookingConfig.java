package it.epicode.be.bookingmanagement.config;

import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import it.epicode.be.bookingmanagement.models.*;

@Configuration
public class BookingConfig {
	
	

	@Bean
	@Scope("prototype")
	public City newCity(String n) {
		return City.builder().name(n).build();
	}
	
	@Bean
	@Scope("prototype")
	public User newUser(String fn, String un, String e) {
		return User.builder().fullname(fn).username(un).email(e).build();
	}
	
	@Bean
	@Scope("prototype")
	public Building newBuilding(String n, String a, City c) {
		return Building.builder().name(n).address(a).city(c).build();
	}
	
	@Bean
	@Scope("prototype")
	public WorkStation newWorkStation(String d, RoomType t, int c, Building b) {
		return WorkStation.builder().description(d).type(t).capacity(c).building(b).build();
	}
	
	@Bean
	@Scope("prototype")
	public Booking newBooking(LocalDate d, User u, WorkStation w) {
		return Booking.builder().date(d).user(u).workstation(w).build();
	}
	

}
