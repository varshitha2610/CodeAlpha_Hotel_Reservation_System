import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean availability) {
        this.isAvailable = availability;
    }
}

class Reservation {
    private static int reservationCount = 0;
    private int reservationId;
    private String guestName;
    private Room room;
    private int nights;
    private double totalCost;

    public Reservation(String guestName, Room room, int nights) {
        this.reservationId = ++reservationCount;
        this.guestName = guestName;
        this.room = room;
        this.nights = nights;
        this.totalCost = nights * room.getPrice();
        room.setAvailability(false);
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void displayReservationDetails() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Number: " + room.getRoomNumber());
        System.out.println("Category: " + room.getCategory());
        System.out.println("Nights: " + nights);
        System.out.println("Total Cost: $" + totalCost);
    }
}

class Hotel {
    private List<Room> rooms;
    private HashMap<Integer, Reservation> reservations;

    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new HashMap<>();
    }

    public void addRoom(int roomNumber, String category, double price) {
        rooms.add(new Room(roomNumber, category, price));
    }

    public void searchRooms(String category) {
        System.out.println("Available rooms in category: " + category);
        for (Room room : rooms) {
            if (room.isAvailable() && room.getCategory().equalsIgnoreCase(category)) {
                System.out.println("Room Number: " + room.getRoomNumber() + ", Price: $" + room.getPrice());
            }
        }
    }

    public void makeReservation(String guestName, int roomNumber, int nights) {
        Room room = findRoomByNumber(roomNumber);
        if (room != null && room.isAvailable()) {
            Reservation reservation = new Reservation(guestName, room, nights);
            reservations.put(reservation.getReservationId(), reservation);
            System.out.println("Reservation successful! Reservation ID: " + reservation.getReservationId());
        } else {
            System.out.println("Room not available for reservation.");
        }
    }

    public void viewReservation(int reservationId) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation != null) {
            reservation.displayReservationDetails();
        } else {
            System.out.println("Reservation ID not found.");
        }
    }

    public void processPayment(int reservationId, double amount) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation != null) {
            if (amount >= reservation.getTotalCost()) {
                System.out.println("Payment successful! Total cost: $" + reservation.getTotalCost());
            } else {
                System.out.println("Insufficient payment. Amount due: $" + (reservation.getTotalCost() - amount));
            }
        } else {
            System.out.println("Invalid reservation ID.");
        }
    }

    private Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.addRoom(101, "Standard", 100);
        hotel.addRoom(102, "Standard", 100);
        hotel.addRoom(201, "Deluxe", 200);
        hotel.addRoom(202, "Deluxe", 200);
        hotel.addRoom(301, "Suite", 300);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Reservation System");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make Reservation");
            System.out.println("3. View Reservation Details");
            System.out.println("4. Process Payment");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid input
                continue;
            }
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter room category (Standard/Deluxe/Suite): ");
                    String category = scanner.next();
                    hotel.searchRooms(category);
                    break;
                case 2:
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.next();
                    System.out.print("Enter room number: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid room number. Please enter a valid number.");
                        scanner.next();
                        continue;
                    }
                    int roomNumber = scanner.nextInt();
                    System.out.print("Enter number of nights: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid number of nights. Please enter a valid number.");
                        scanner.next();
                        continue;
                    }
                    int nights = scanner.nextInt();
                    hotel.makeReservation(guestName, roomNumber, nights);
                    break;
                case 3:
                    System.out.print("Enter reservation ID: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid reservation ID. Please enter a valid number.");
                        scanner.next();
                        continue;
                    }
                    int reservationId = scanner.nextInt();
                    hotel.viewReservation(reservationId);
                    break;
                case 4:
                    System.out.print("Enter reservation ID: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid reservation ID. Please enter a valid number.");
                        scanner.next();
                        continue;
                    }
                    reservationId = scanner.nextInt();
                    System.out.print("Enter payment amount: ");
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Invalid payment amount. Please enter a valid number.");
                        scanner.next();
                        continue;
                    }
                    double amount = scanner.nextDouble();
                    hotel.processPayment(reservationId, amount);
                    break;
                case 5:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}