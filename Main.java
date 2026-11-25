import java.util.*;

class Slot {
    String slotId;
    String time;
    int capacity;

    Slot(String slotId, String time, int capacity) {
        this.slotId = slotId;
        this.time = time;
        this.capacity = capacity;
    }
}

class Booking {
    String name;
    int age;
    String slotId;
    String bookingId;

    Booking(String name, int age, String slotId) {
        this.name = name;
        this.age = age;
        this.slotId = slotId;
        this.bookingId = "";
    }
}

class BookingSystem {

    Slot[] slots = new Slot[5];

    Queue<Booking> requestQueue = new LinkedList<>();

    HashMap<String, Booking> bookings = new HashMap<>();

    int bookingCounter = 1001;

    BookingSystem() {
        slots[0] = new Slot("S101", "10:00 AM", 3);
        slots[1] = new Slot("S102", "11:00 AM", 2);
        slots[2] = new Slot("S103", "12:00 PM", 3);
        slots[3] = new Slot("S104", "01:00 PM", 2);
        slots[4] = new Slot("S105", "02:00 PM", 3);
    }

    // Display available slots
    void displaySlots() {
        System.out.println("\n--- AVAILABLE VACCINATION SLOTS ---");

        for (int i = 0; i < slots.length; i++) {
            System.out.println(slots[i].slotId + " | "
                    + slots[i].time + " | Capacity: "
                    + slots[i].capacity);
        }
    }

    // Add booking request to queue
    void addRequest(String name, int age, String slotId) {

        if (name.trim().isEmpty() || age <= 0) {
            System.out.println("Invalid details!");
            return;
        }

        boolean slotFound = false;

        for (int i = 0; i < slots.length; i++) {
            if (slots[i].slotId.equalsIgnoreCase(slotId)) {
                slotFound = true;
                break;
            }
        }

        if (!slotFound) {
            System.out.println("Invalid Slot ID!");
            return;
        }

        Booking newBooking = new Booking(name, age, slotId);

        requestQueue.add(newBooking);

        System.out.println("Booking request added to queue.");
    }

    // Process next booking request
    void processNextRequest() {

        if (requestQueue.isEmpty()) {
            System.out.println("No pending booking requests.");
            return;
        }

        Booking next = requestQueue.poll();

        Slot selectedSlot = null;

        for (int i = 0; i < slots.length; i++) {
            if (slots[i].slotId.equalsIgnoreCase(next.slotId)) {
                selectedSlot = slots[i];
                break;
            }
        }

        System.out.println("\nProcessing next request...");

        if (selectedSlot == null) {
            System.out.println("Invalid slot!");
            return;
        }

        if (selectedSlot.capacity <= 0) {
            System.out.println("Slot is full!");
            return;
        }

        selectedSlot.capacity--;

        next.bookingId = "VAX" + bookingCounter++;

        bookings.put(next.bookingId, next);

        System.out.println("\nBooking Confirmed!");
        System.out.println("Booking ID: " + next.bookingId);
        System.out.println("Name: " + next.name);
        System.out.println("Age: " + next.age);
        System.out.println("Slot: " + next.slotId);
        System.out.println("Time: " + selectedSlot.time);
        System.out.println("Remaining Capacity: " + selectedSlot.capacity);
    }

    // Display pending queue
    void displayQueue() {

        if (requestQueue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        System.out.println("\n--- PENDING REQUESTS ---");

        for (Booking b : requestQueue) {
            System.out.println(b.name + " | Age: " + b.age
                    + " | Slot: " + b.slotId);
        }
    }

    // Search booking using HashMap
    void searchBooking(String bookingId) {

        Booking b = bookings.get(bookingId);

        if (b == null) {
            System.out.println("Booking not found!");
        } else {
            System.out.println("\n--- BOOKING DETAILS ---");
            System.out.println("Booking ID: " + b.bookingId);
            System.out.println("Name: " + b.name);
            System.out.println("Age: " + b.age);
            System.out.println("Slot ID: " + b.slotId);
        }
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookingSystem system = new BookingSystem();

        int choice;

        do {
            System.out.println("\n===== VACCINATION SLOT BOOKING SYSTEM =====");
            System.out.println("1. View Available Slots");
            System.out.println("2. Add Booking Request");
            System.out.println("3. Process Next Request");
            System.out.println("4. View Pending Queue");
            System.out.println("5. Search Booking by ID");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    system.displaySlots();
                    break;

                case 2:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Slot ID: ");
                    String slotId = sc.nextLine();

                    system.addRequest(name, age, slotId);
                    break;

                case 3:
                    system.processNextRequest();
                    break;

                case 4:
                    system.displayQueue();
                    break;

                case 5:
                    System.out.print("Enter Booking ID: ");
                    String bookingId = sc.nextLine();

                    system.searchBooking(bookingId);
                    break;

                case 6:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }
}