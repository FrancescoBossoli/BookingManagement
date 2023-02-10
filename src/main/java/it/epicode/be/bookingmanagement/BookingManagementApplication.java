package it.epicode.be.bookingmanagement;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import it.epicode.be.bookingmanagement.config.BookingConfig;
import it.epicode.be.bookingmanagement.models.*;
import it.epicode.be.bookingmanagement.services.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@Component
public class BookingManagementApplication implements CommandLineRunner {
	
	@Autowired
	private CityService cS;
	@Autowired
	private UserService uS;
	@Autowired
	private BuildingService bgS;
	@Autowired
	private BookingService bkS;
	@Autowired
	private WorkStationService wS;	
	@Autowired
	private BookingConfig bC;
	private ApplicationContext ctx = new AnnotationConfigApplicationContext(BookingConfig.class);
	
	private Scanner in = new Scanner(System.in);	
	

	public static void main(String[] args) {
		SpringApplication.run(BookingManagementApplication.class, args);
	}		
	
	@Override
	public void run(String... args) throws Exception {
		
		getInitialData();
		mainMenu();
		
		
		((AnnotationConfigApplicationContext)ctx).close();
	}
	
	public City save(String n) {
		City c = (City)ctx.getBean("newCity", n);
		cS.save(c);
		return c;
	}
	
	public Building save(String n, String a, City c) {
		Building b = (Building)ctx.getBean("newBuilding", n, a, c);
		bgS.save(b);
		return b;
	}
	
	public User save(String fn, String un, String e) {
		User u = (User)ctx.getBean("newUser", fn, un, e);
		uS.save(u);
		return u;
	}
	
	public WorkStation save(String d, RoomType t, int c, Building b) {
		WorkStation w = (WorkStation)ctx.getBean("newWorkStation", d, t, c, b);
		wS.save(w);
		return w;
	}
	
	public Booking save(LocalDate d, User u, WorkStation w) {
		Booking b = (Booking)ctx.getBean("newBooking", d, u, w);
		bkS.save(b);
		return b;
	}
	
	public void getInitialData() {
		City c1 = save("New York");
		City c2 = save("Chicago");
		City c3 = save("Philadelphia");
		User u1 = save("Mario Rossi", "Marius", "mariorossi@gmail.com");
		User u2 = save("Luca Neri", "Blackie", "lucaneri@gmail.com");
		User u3 = save("Luigi Verdi", "Greenhouse", "luigiverdi@hotmail.com");
		User u4 = save("Marco Bianchi", "DoctorM", "marcobianchi@tiscali.it");
		User u5 = save("Sara Grandi", "Sarexx", "saragrandi@libero.it");
		User u6 = save("Laura Esposito", "Lauretta", "lauraesposito@aol.com");
		Building bg1 = save("Empire State Building", "20 West 34th Street", c1);
		Building bg2 = save("Willis Tower", "233 S Wacker Dr", c2);
		Building bg3 = save("Independence Hall", "520 Chestnut St", c3);
		WorkStation w1 = save("Vip Business Hall", RoomType.CONFERENCE_ROOM, 20, bg1);
		WorkStation w2 = save("Vip Business Conference Room", RoomType.CONFERENCE_ROOM, 30, bg2);
		WorkStation w3 = save("Private Vip Room", RoomType.PRIVATE, 10, bg1);
		WorkStation w4 = save("Royal Suite", RoomType.PRIVATE, 15, bg1);
		WorkStation w5 = save("Major Club Hall", RoomType.OPENSPACE, 20, bg2);
		WorkStation w6 = save("Indipendence Room", RoomType.CONFERENCE_ROOM, 30, bg3);
		Booking bk1 = save(LocalDate.now(), u1, w1);
		Booking bk2 = save(LocalDate.now(), u2, w1);
		Booking bk3 = save(LocalDate.now(), u3, w1);
		Booking bk4 = save(LocalDate.now(), u4, w1);
		Booking bk5 = save(LocalDate.now(), u5, w1);
		Booking bk6 = save(LocalDate.now(), u6, w1);
		Booking bk7 = save(LocalDate.now().plusDays(2), u1, w2);
		Booking bk8 = save(LocalDate.now().plusDays(1), u2, w3);
		Booking bk9 = save(LocalDate.now().plusDays(2), u3, w4);
		Booking bk10 = save(LocalDate.now().plusDays(3), u4, w5);
		Booking bk11 = save(LocalDate.now().plusDays(6), u5, w6);
		Booking bk12 = save(LocalDate.now().plusDays(4), u6, w2);
		Booking bk13 = save(LocalDate.now().plusDays(3), u1, w3);
		Booking bk14 = save(LocalDate.now().plusDays(4), u2, w4);
		Booking bk15 = save(LocalDate.now().plusDays(5), u3, w6);
		Booking bk16 = save(LocalDate.now().plusDays(5), u4, w5);
		Booking bk17 = save(LocalDate.now().plusDays(3), u5, w4);
		Booking bk18 = save(LocalDate.now().plusDays(6), u6, w3);
		Booking bk19 = save(LocalDate.now().plusDays(6), u2, w3);
		Booking bk20 = save(LocalDate.now().plusDays(6), u4, w3);		
	}
	
	public void mainMenu() {
		log.info("""
                                   ---------------------------------------------------------------- 
                                   Interactive Online Booking Service: 
                                   1. Add a new Entity to the Database
                                   2. Book a Workstation for a specified Date
                                   3. Search for Available Workstations 
                                   4. Print Lists 
                                                                       
                                   0. Turn Off the Interactive Archive 
                                   """);
			
		int selection = 10;
		outerloop:
		while (selection != 0) {
			try {
				selection = Integer.parseInt(in.nextLine());
				switch (selection) {
					case 1 -> {
						menuAdd();
                        selection = Integer.parseInt(in.nextLine());
                        switch (selection) {
                        	case 1 -> {
                        		log.info("What's the user's Full Name?");
                        		String fn = in.nextLine();
                        		if (fn.length() < 5) throw new Exception("A person's Full Name should be at least 5 characters long.");
                        		log.info("What's the user's Nickname?");
                        		String un = in.nextLine();
                        		if (un.length() < 3) throw new Exception("A person's Nickname should be at least 3 characters long.");
                        		log.info("What's the user's e-mail?");
                        		String e = in.nextLine();
                        		if (e.length() < 5) throw new Exception("A person's e-mail should be at least 7 characters long.");
                        		save(fn,un,e);         
                        	}
                        	case 2 -> {
                        		log.info("What's the City's name?");
                        		String cn = in.nextLine();
                        		if (cn.length() < 3) throw new Exception("A City's Name should be at least 3 characters long.");
                        		save(cn);
                        	}
                        	case 3 -> {
                        		log.info("What's the Building's name?");
                        		String bn = in.nextLine();
                        		if (bn.length() < 5) throw new Exception("A Building's Name should be at least 5 characters long.");
                        		log.info("What's the Building's address?");
                        		String a = in.nextLine();
                        		if (a.length() < 5) throw new Exception("A Building's Address should be at least 5 characters long.");
                        		log.info("What's the Building's City?");
                        		String c = in.nextLine();
                        		if (c.length() < 3) throw new Exception("A City's Name should be at least 3 characters long.");
                        		List<City> lc = cS.getCityByName(c);
                        		if (lc.size() == 0) save(c);
                        		save(bn,a,cS.getCityByName(c).get(0));
                        	}
                        	case 4 -> {
                        		log.info("Insert the WorkStation's description.");
                        		String d = in.nextLine();
                        		if (d.length() < 5) throw new Exception("A WorkStation's description should be at least 5 characters long.");
                        		log.info("What's the WorkStation's type? (Private, Openspace, Conference_Room)");                        		
                        		try {
                        			RoomType rt = RoomType.valueOf(in.nextLine().toUpperCase());                        		
                        			log.info("What's the WorkStation's capacity?");
                        			int c = Integer.parseInt(in.nextLine());
                        			if (c<1) throw new Exception("A Room's Capacity should be greater than that.");
                        			log.info("What's the Building's name?");
                        			String bn = in.nextLine();
                        			if (bn.length() < 5) throw new Exception("A Building's Name should be at least 5 characters long.");
                        			List<Building> lb = bgS.getBuildingByName(bn);
                        			if (lb.size() == 0) throw new Exception("The specified Building needs to be correctly put in the Database.");
                        			save(d,rt,c,lb.get(0));
                        		} catch (Exception e) {
                        			log.info("That option wasn't available.");
                        		}
                        	}	
                        	case 9 -> {}            					
            				case 0 -> {break outerloop;}
            				default -> log.info("That option wasn't available.");
                        }          
					}
					case 2 -> {
						log.info("Insert the Day you wish to have a Reservation (YYYY-MM-DD).");
						LocalDate d = LocalDate.parse(in.nextLine());
						log.info("What's your Username?");
                		String un = in.nextLine();
                		List<User> lu = uS.getUsersByUsername(un);
                		if (lu.size() == 0) throw new Exception("There isn't an User with that UserName.");
                		log.info("What's the Id of the WorkStation you wish to book out?");
                		Long id = Long.parseLong(in.nextLine());
                		Optional<WorkStation> w = wS.getWorkStationById(id);
                		if (w.isPresent() == false) throw new Exception("There isn't a WorkStation with that Id.");               		
                		WorkStation ws = w.get();
            			save(d,lu.get(0),ws);						
					}
					case 3 -> {
						log.info("What kind of WorkStation are you searching for? (Private, Openspace, Conference_Room)");
						try {
                			RoomType rt = RoomType.valueOf(in.nextLine().toUpperCase());
                			log.info("Select the Id of the City you want to book a WorkStation in.");
                			cS.printList(cS.getAllCities());
                			Long id2 = Long.parseLong(in.nextLine());
                			log.info("d");
                			wS.printList(wS.getWorkStationByTypeAndCityId(rt, id2));                			
						} catch (Exception e) {
                			log.info("That option wasn't available.");
                		}
					}
					case 4 -> {
						menuPrint();
                        selection = Integer.parseInt(in.nextLine());
                        switch (selection) {
                        	case 1 -> uS.printList(uS.getAllUsers());
                        	case 2 -> cS.printList(cS.getAllCities());
                        	case 3 -> bgS.printList(bgS.getAllBuildings());
                        	case 4 -> wS.printList(wS.getAllWorkStations());
                        	case 5 -> bkS.printList(bkS.getAllBookings());
                        	case 9 -> {}            					
                        	case 0 -> {break outerloop;}		
                        	default -> log.info("That option wasn't available.");
                        }
					}
					case 0 -> {break outerloop;}					
				}
				mainMenu();	
			} catch (NumberFormatException e) {
				log.info("You have to input a valid option number");
				mainMenu();	
			} catch(DateTimeParseException e) {
				log.info("The Date inserted isn't in a recognizable format.");
				mainMenu();
			} catch(Exception e) {
				log.info(e.getMessage());
			}
		}
	}
	
	public void menuAdd() {
		System.out.println("""
                                   	---------------------------------------------------------------- 
                                   	What kind of Entity do you want to add to the Database? 
                                   	1. A new User
                                   	2. A new City 
                                   	3. A new Building 
                                   	4. A new WorkStation 
                                    
                                   	9. Go Back 
                                   	0. Turn Off the Interactive Archive 
                                   	""");
	}
	
	public void menuPrint() {
		System.out.println("""
                					---------------------------------------------------------------- 
                					What kind of List do you want to print? 
                					1. Users List
                					2. Cities List
                					3. Buildings List
                					4. WorkStations List
                					5. Bookings List
                 
                					9. Go Back 
                					0. Turn Off the Interactive Archive 
                					""");
}

}
