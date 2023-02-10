package it.epicode.be.bookingmanagement.repositories;

import it.epicode.be.bookingmanagement.models.Booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM bookings WHERE date = :d AND workstation_id = :i")
	int countBookingsByDateAndWorkStation(@Param("d") LocalDate d, @Param("i") Long i);
	
	@Query(nativeQuery = true, value = "SELECT * FROM bookings WHERE date = :d AND user_id = :i")
	List<Booking> findBookingsByDateAndUser(@Param("d") LocalDate d, @Param("i") Long i);
	

}
