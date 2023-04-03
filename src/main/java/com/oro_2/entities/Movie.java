package com.oro_2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name= "movies")
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Movie
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String movieTitle;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "movie")
	private List<Showing> showings;
}
