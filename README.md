# Smart Railway Reservation Management System

## Project Overview
The Smart Railway Reservation Management System is a menu-driven Java application that allows users to book railway tickets, view existing bookings, search for bookings using a booking ID, and cancel tickets. It stores passenger and journey details in an embedded Apache Derby database.

## Features
- **Ticket Booking:** Book tickets by entering passenger details and journey information.
- **Journey Details:** Store source station, destination station, and journey date.
- **Automatic Fare Calculation:** Calculate fares based on travel class and number of seats.
- **View Bookings:** Display all stored reservations.
- **Search Booking:** Retrieve booking details using a unique booking ID.
- **Cancel Booking:** Cancel an existing reservation.
- **Persistent Storage:** Store booking records in an embedded Derby database.
- **Menu-Driven Interface:** Simple console-based navigation.

## Technologies Used
- **Language:** Java
- **Database:** Apache Derby (Embedded)
- **Database Connectivity:** JDBC
- **Concepts:** Object-Oriented Programming (OOP), SQL, Exception Handling

## Prerequisites
- Java Development Kit (JDK)
- Visual Studio Code
- Java extensions for VS Code
- Apache Derby libraries compatible with your Java setup

**Note:** Apache Derby is not included in most modern JDK installations. Add the required Derby libraries to your project's classpath if they are not already available.

## How to Run
1. Open the project folder in VS Code.
2. Ensure the JDK and required Derby libraries are configured.
3. Compile `RailwayDerbySystem.java` using the configured Java compiler.
4. Run the `RailwayDerbySystem` class.
5. Follow the console menu to book, view, search, or cancel tickets.

## Database
The application uses an embedded Derby database with the connection URL:

`jdbc:derby:railwayDB;create=true`

The database is created automatically when the application connects for the first time, and booking records persist between runs.

## Future Enhancements
- Train selection and automatic train allocation
- Seat availability tracking
- Passenger input and journey-date validation
- Graphical user interface
- Booking history and printable tickets
