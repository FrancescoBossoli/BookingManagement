package it.epicode.be.bookingmanagement.models;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	@ManyToOne
	private User user;
	@ManyToOne
	private WorkStation workstation;
	
	@Override
	public String toString() {
		return "Id: " + String.format("%1$-"+ 5 + "s", this.getId()) + "Date: " + String.format("%1$-"+ 13 + "s",this.getDate()) 
			 + "User: " + String.format("%1$-"+ 13 + "s", this.getUser().getUsername()) + "WorkStation: " 
			 + String.format("%1$-"+ 25 + "s", this.getWorkstation().getDescription());
	}
}
