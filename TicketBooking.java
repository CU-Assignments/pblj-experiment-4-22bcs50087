import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TicketBookingSystem {
    private int availableSeats;
    private final Lock lock = new ReentrantLock();

    public TicketBookingSystem(int totalSeats) {
        this.availableSeats = totalSeats;
    }

    public void bookTicket(String customerName) {
        lock.lock();
        try {
            if (availableSeats > 0) {
                System.out.println(customerName + " booked a seat. Seats left: " + (availableSeats - 1));
                availableSeats--;
            } else {
                System.out.println("No seats available for " + customerName);
            }
        } finally {
            lock.unlock();
        }
    }
}

class BookingThread extends Thread {
    private TicketBookingSystem bookingSystem;
    private String customerName;

    public BookingThread(TicketBookingSystem bookingSystem, String customerName) {
        this.bookingSystem = bookingSystem;
        this.customerName = customerName;
    }

    @Override
    public void run() {
        bookingSystem.bookTicket(customerName);
    }
}

public class TicketBooking {
    public static void main(String[] args) {
        TicketBookingSystem bookingSystem = new TicketBookingSystem(5); // Total seats available

        // Creating VIP booking threads
        BookingThread vip1 = new BookingThread(bookingSystem, "VIP Customer 1");
        BookingThread vip2 = new BookingThread(bookingSystem, "VIP Customer 2");

        // Creating regular booking threads
        BookingThread regular1 = new BookingThread(bookingSystem, "Regular Customer 1");
        BookingThread regular2 = new BookingThread(bookingSystem, "Regular Customer 2");
        BookingThread regular3 = new BookingThread(bookingSystem, "Regular Customer 3");

        // Setting higher priority for VIP customers
        vip1.setPriority(Thread.MAX_PRIORITY);
        vip2.setPriority(Thread.MAX_PRIORITY);

        // Starting the threads
        vip1.start();
        vip2.start();
        regular1.start();
        regular2.start();
        regular3.start();

        // Wait for all threads to finish
        try {
            vip1.join();
            vip2.join();
            regular1.join();
            regular2.join();
            regular3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Booking process completed.");
    }
}
