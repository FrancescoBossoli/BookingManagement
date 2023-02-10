package it.epicode.be.bookingmanagement.repositories;

import it.epicode.be.bookingmanagement.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE username = :u")
	List<User> findUserByUsername(@Param("u") String u);
	
	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE email = :e")
	List<User> findUserByEmail(@Param("e") String e);
	
	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE username = :u AND fullname = :f AND email = :e")
	List<User> findExistingUser(@Param("u") String u, @Param("f") String f, @Param("e") String e);
}
