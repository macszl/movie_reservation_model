# movie_reservation_model

This is a Spring application with a database model, repositories, and custom queries. It uses Postgres as the database, which is set up using docker-compose.

## Getting Started

To run the application, you need to have Docker and Docker Compose installed on your machine. Once you have installed these dependencies, follow the steps below:

1. Clone the repository to your local machine.
2. Navigate to the project root directory.
3. Run `docker-compose up` to start the Postgres container.
4. Run the OroApplicationTest to run the tests.

## Entities

The application contains the following entities:

- Hall
- Movie
- Reservation
- Seat
- Showing
- TicketType
- User

## Repositories

The following repositories are available in the application:

- HallRepository
- MovieRepository
- ReservationRepository
- SeatRepository
- ShowingRepository
- TicketTypeRepository
- UserRepository

## Running the application

The application does not contain any endpoints. The main entry point of the application is OroApplicationTest, which creates and populates a database and tests the custom queries.

To run the tests, simply run the Oro2ApplicationTests. The tests will automatically create and populate a database. 

There is a number of custom queries available in OroApplicationTest. The tests will ensure that custom queries are working as expected.

