package com.oro_2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "showings",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"movie_id", "hall_id", "show_time"}))
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Showing
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@ManyToOne
	@JoinColumn(name = "hall_id")
	private Hall hall;

	@Column(name = "show_time", nullable = false)
	private LocalDateTime showTime;
}
