package it.epicode.be.bookingmanagement.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workstations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkStation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	@Enumerated(EnumType.STRING)
	private RoomType type;
	private int capacity;
	@ManyToOne
	private Building building;
	@OneToMany(mappedBy = "workstation", cascade = CascadeType.ALL)
	private List<Booking> bookings;
	
	@Override
	public String toString() {
		return "Id: " + String.format("%1$-"+ 5 + "s", this.getId()) + "Description: " + String.format("%1$-"+ 32 + "s",this.getDescription()) 
			 + "Capacity: " + String.format("%1$-"+ 5 + "s", this.getCapacity()) + "Type: " + String.format("%1$-"+ 18 + "s", this.getType()) 
			 + "Building: " + this.getBuilding().getName();
	}
}
