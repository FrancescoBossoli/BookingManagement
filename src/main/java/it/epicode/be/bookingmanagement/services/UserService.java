package it.epicode.be.bookingmanagement.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.epicode.be.bookingmanagement.models.User;
import it.epicode.be.bookingmanagement.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository uR;
	
	//This method saves a User in the database if username isn't already taken and if the email isn't already registered
	public void save(User u) {
		List<User> l1 = getUsersByUsername(u.getUsername());
		List<User> l2 = getUsersByEmail(u.getEmail());
		if (l1.size() == 0 && l2.size() == 0) {
			uR.save(u);
			log.info("The User has been saved in the Database.");			
		}
		else if (l2.size() != 0) log.info("The specified email is already associated to an existing account.");
		else log.info("The selected Username is already taken. You should choose another one.");
	}
	
	public Optional<User> getUserById(Long id) {
		return uR.findById(id);
	}
	
	public List<User> getUsersByUsername(String u) {
		return uR.findUserByUsername(u);
	}
	
	public List<User> getUsersByEmail(String e) {
		return uR.findUserByEmail(e);
	}	
	
	public List<User> getAllUsers(){
		return uR.findAll();
	}
	
	public List<User> getExistingUser(String u, String f, String e) {
		return uR.findExistingUser(u, f, e);
	}
	
	public void deleteUserById(Long id) {
		uR.deleteById(id);
	}
	
	public void printList(List<User> list) {
		for (User l : list)
			log.info(l.toString());
	}
}
