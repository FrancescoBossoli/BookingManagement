package it.epicode.be.bookingmanagement.repositories;

import it.epicode.be.bookingmanagement.models.Building;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM buildings WHERE name = :n AND address = :a AND city_id =:i")
	List<Building> findExistingBuilding(@Param("n") String n, @Param("a") String a, @Param("i") Long i);
	
	@Query(nativeQuery = true, value = "SELECT * FROM buildings WHERE name = :n AND address = :a")
	List<Building> findBuildingsByNameAndAddress(@Param("n") String n, @Param("a") String a);
	
	@Query(nativeQuery = true, value = "SELECT * FROM buildings WHERE name = :n")
	List<Building> findBuildingByName(@Param("n") String n);
}
