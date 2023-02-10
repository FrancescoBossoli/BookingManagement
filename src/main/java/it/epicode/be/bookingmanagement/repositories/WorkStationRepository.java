package it.epicode.be.bookingmanagement.repositories;

import it.epicode.be.bookingmanagement.models.RoomType;
import it.epicode.be.bookingmanagement.models.WorkStation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkStationRepository extends JpaRepository<WorkStation, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM workstations WHERE description = :d AND type = :t AND capacity = :c AND building_id =:i")
	List<WorkStation> findExistingWorkStation(@Param("d") String d, @Param("t") String t, @Param("c") int c, @Param("i") Long i);
	
	@Query(nativeQuery = true, value = "SELECT * FROM workstations w INNER JOIN buildings b ON w.building_id = b.id WHERE w.type = :t AND b.city_id = :i")
	List<WorkStation> findWorkStationByTypeAndCityId(@Param("t") String t, @Param("i") Long i);
}
