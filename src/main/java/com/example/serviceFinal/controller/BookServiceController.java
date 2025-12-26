package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.BookService;
import com.example.serviceFinal.entity.Service;
import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.repository.ServiceRepository;
import com.example.serviceFinal.repository.UserRepository;
import com.example.serviceFinal.service.BookServiceService;
import com.example.serviceFinal.service.EmailService;
import java.util.List;
import java.util.Optional;
// import javax.management.RuntimeErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookservices")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:3001",
    "http://localhost:3002",
    "http://127.0.0.1:3000",
    "http://127.0.0.1:3001"
})public class BookServiceController {

  @Autowired
  private BookServiceService bookServiceService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ServiceRepository serviceRepository;

  @Autowired
  private EmailService emailService;

  @PostMapping("/book")
  public ResponseEntity<?> bookService(@RequestBody BookService bookService) {
    try {
      // 1. Validate user ID
      if (bookService.getUserId() <= 0) {
        return ResponseEntity.badRequest().body("User Id is required.");
      }

      // 2. Find user
      Optional<User> userOptional = userRepository.findById(
        bookService.getUserId()
      );
      if (userOptional.isEmpty()) {
        return ResponseEntity.badRequest().body("User not found.");
      }
      User user = userOptional.get();

      // 3. Validate service ID
      if (bookService.getServiceId() <= 0) {
        return ResponseEntity.badRequest().body("Service Id is required.");
      }

      // 4. Find service
      Optional<Service> serviceOptional = serviceRepository.findById(
        bookService.getServiceId()
      );
      if (serviceOptional.isEmpty()) {
        return ResponseEntity.badRequest().body("Service not found");
      }
      Service service = serviceOptional.get(); // Get the Service object

      // 5. Set default status if not provided
      if (bookService.getStatus() == null) {
        bookService.setStatus(BookService.Status.REQUESTED);
      }

      // 6. Save the booking
      BookService savedBooking = bookServiceService.saveBookService(
        bookService
      );

      // 7. Update user with the booked service ID
      user.setBookServiceId(savedBooking.getId());
      userRepository.save(user);

      // 8. Send confirmation email
      sendBookingConfirmationEmail(user, service, savedBooking); // UNCOMMENT THIS LINE

      return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        "Error booking service"
      );
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<BookService>> getAllBookedService() {
    List<BookService> allBookedService =
      bookServiceService.getAllBookServices();
    return ResponseEntity.ok(allBookedService);
  }

  @GetMapping("/{id}")
  public Optional<BookService> getBookServiceById(@PathVariable int id) {
    Optional<BookService> bookservice = bookServiceService.getBookServiceById(
      id
    );
    return bookservice;
  }

  @DeleteMapping("/delete/{id}")
  public String deleteBookedService(@PathVariable int id) {
    bookServiceService.deleteBookService(id);
    return "Booked Service can deleted sucessfully";
  }

  private void sendBookingConfirmationEmail(
    User user,
    Service service,
    BookService booking
  ) {
    try {
      String to = user.getEmail();
      String subject = "Booking Confirmation - " + service.getServiceName();

      // HTML email
      String htmlContent = String.format(
        """
        <!DOCTYPE html>
        <html>
        <body style="font-family: Arial, sans-serif; line-height: 1.6;">
            <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd;">
                <h2 style="color: #4CAF50;">Booking Confirmed!</h2>
                <p>Dear <strong>%s</strong>,</p>
                <p>Your booking has been successfully confirmed.</p>

                <div style="background: #f9f9f9; padding: 15px; margin: 20px 0; border-left: 4px solid #4CAF50;">
                    <h3>Booking Details:</h3>
                    <p><strong>Service:</strong> %s</p>
                    <p><strong>Booking ID:</strong> %d</p>
                    <p><strong>Date & Time:</strong> %s</p>
                    <p><strong>Status:</strong> %s</p>
                    <p><strong>Message:</strong> %s</p>
                </div>

                <p>We'll contact you shortly regarding your booking.</p>

                <p>Thank you for choosing our service!</p>

                <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">

                <p style="color: #666; font-size: 12px;">
                    This is an automated email. Please do not reply.
                </p>
            </div>
        </body>
        </html>
        """,
        user.getName(),
        service.getServiceName(),
        booking.getId(),
        booking.getTime(),
        booking.getStatus().toString(),
        booking.getMessage() != null ? booking.getMessage() : "Not specified"
      );

      emailService.sendHtmlEmail(to, subject, htmlContent);

      System.out.println("Booking confirmation email sent to: " + to);
    } catch (Exception e) {
      System.err.println("Failed to send email: " + e.getMessage());
      // Don't throw, just log the error
    }
  }
}
