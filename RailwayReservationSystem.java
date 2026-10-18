package project;

import java.sql.*;
import java.util.*;

public class RailwayReservationSystem {

    static final String DB_URL = "jdbc:derby:railwayDB;create=true";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        createTableIfNotExists();

        int choice;
        do {
            System.out.println("\n==== SMART RAILWAY RESERVATION SYSTEM ====");
            System.out.println("1. Book Ticket");
            System.out.println("2. View All Bookings");
            System.out.println("3. Search Booking");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> bookTicket();
                case 2 -> viewBookings();
                case 3 -> searchBooking();
                case 4 -> cancelBooking();
                case 5 -> System.out.println("System Closed.");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    // Create table once
    static void createTableIfNotExists() {
        try (Connection con = DriverManager.getConnection(DB_URL)) {
            Statement st = con.createStatement();
            try {
                st.executeUpdate(
                    "CREATE TABLE bookings (" +
                    "booking_id INT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "source VARCHAR(50), " +
                    "destination VARCHAR(50), " +
                    "journey_date VARCHAR(20), " +
                    "class_type VARCHAR(20), " +
                    "seats INT, " +
                    "fare DOUBLE)"
                );
                System.out.println("Database initialized.");
            } catch (SQLException e) {
                // Table already exists
            }
        } catch (Exception e) {
            System.out.println("DB Error!");
        }
    }

    // Book Ticket
    static void bookTicket() {
        try (Connection con = DriverManager.getConnection(DB_URL)) {

            System.out.print("Passenger Name: ");
            String name = sc.nextLine();

            System.out.print("Source Station: ");
            String source = sc.nextLine();

            System.out.print("Destination Station: ");
            String destination = sc.nextLine();

            System.out.print("Journey Date (DD-MM-YYYY): ");
            String date = sc.nextLine();

            System.out.print("Class (Sleeper / AC): ");
            String classType = sc.nextLine();

            System.out.print("Number of Seats: ");
            int seats = sc.nextInt();
            sc.nextLine();

            double fare = calculateFare(classType, seats);
            int bookingId = new Random().nextInt(9000) + 1000;

            String sql = "INSERT INTO bookings VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, bookingId);
            ps.setString(2, name);
            ps.setString(3, source);
            ps.setString(4, destination);
            ps.setString(5, date);
            ps.setString(6, classType);
            ps.setInt(7, seats);
            ps.setDouble(8, fare);

            ps.executeUpdate();

            System.out.println("\n✅ Ticket Booked Successfully!");
            System.out.println("Booking ID: " + bookingId);
            System.out.println("Total Fare: ₹" + fare);

        } catch (Exception e) {
            System.out.println("Booking Error!");
        }
    }

    // View All
    static void viewBookings() {
        try (Connection con = DriverManager.getConnection(DB_URL)) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM bookings");

            while (rs.next()) {
                System.out.println("\n------------------------------");
                System.out.println("Booking ID : " + rs.getInt("booking_id"));
                System.out.println("Name       : " + rs.getString("name"));
                System.out.println("Route      : " + rs.getString("source") + " → " + rs.getString("destination"));
                System.out.println("Date       : " + rs.getString("journey_date"));
                System.out.println("Class      : " + rs.getString("class_type"));
                System.out.println("Seats      : " + rs.getInt("seats"));
                System.out.println("Fare       : ₹" + rs.getDouble("fare"));
            }

        } catch (Exception e) {
            System.out.println("Display Error!");
        }
    }

    // Search
    static void searchBooking() {
        try (Connection con = DriverManager.getConnection(DB_URL)) {

            System.out.print("Enter Booking ID: ");
            int id = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM bookings WHERE booking_id=?"
            );
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\nBooking Found:");
                System.out.println("Passenger : " + rs.getString("name"));
                System.out.println("Route      : " + rs.getString("source") + " → " + rs.getString("destination"));
                System.out.println("Date       : " + rs.getString("journey_date"));
                System.out.println("Fare       : ₹" + rs.getDouble("fare"));
            } else {
                System.out.println("Booking not found!");
            }

        } catch (Exception e) {
            System.out.println("Search Error!");
        }
    }

    // Cancel
    static void cancelBooking() {
        try (Connection con = DriverManager.getConnection(DB_URL)) {

            System.out.print("Enter Booking ID to cancel: ");
            int id = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM bookings WHERE booking_id=?"
            );
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("✅ Ticket Cancelled Successfully!");
            else
                System.out.println("Booking not found!");

        } catch (Exception e) {
            System.out.println("Cancel Error!");
        }
    }

    static double calculateFare(String classType, int seats) {
        double rate = classType.equalsIgnoreCase("AC") ? 1500 : 500;
        return rate * seats;
    }
}
