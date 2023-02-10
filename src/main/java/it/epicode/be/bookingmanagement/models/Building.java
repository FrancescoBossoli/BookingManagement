package it.epicode.be.bookingmanagement.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
@Table(name = "buildings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Building {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String address;
	@ManyToOne
	private City city;	
	@OneToMany(mappedBy="building", cascade = CascadeType.ALL)
	private List<WorkStation> workstations;
	
	@Override
	public String toString() {
		return "Id: " + String.format("%1$-"+ 5 + "s", this.getId()) + "Name: " + String.format("%1$-"+ 23 + "s",this.getName()) 
			 + "Address: " + String.format("%1$-"+ 22 + "s", this.getAddress()) + "City: " + String.format("%1$-"+ 10 + "s", this.getCity().getName());
	}
}
