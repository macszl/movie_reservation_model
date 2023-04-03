package com.oro_2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "seats",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"hall_id", "row", "column"}))
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int row;

	private int column;

	@ManyToOne
	@JoinColumn(name = "hall_id")
	private Hall hall;
}
