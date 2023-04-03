package com.oro_2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "reservations")
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Reservation
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "showing_id")
	private Showing showing;
	@ManyToOne
	@JoinColumn(name = "seat_id")
	private Seat seat;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "ticket_type_id")
	private TicketType type;

	private LocalDateTime buyTime;
}
