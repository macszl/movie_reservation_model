package com.oro_2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name= "halls")
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Hall
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	String hallLocation;

	@OneToMany( fetch = FetchType.EAGER ,mappedBy = "hall")
	private List<Showing> showings;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "hall")
	private List<Seat> seats;
}
