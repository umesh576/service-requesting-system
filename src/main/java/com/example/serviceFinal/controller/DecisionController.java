package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.BookService;
import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.entity.Worker;
import com.example.serviceFinal.repository.BookServiceRepository;
import com.example.serviceFinal.repository.UserRepository;
import com.example.serviceFinal.repository.WorkerRepository;
import com.example.serviceFinal.service.EmailService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Add these imports if missing

@RestController
@RequestMapping("/bookServiceDecion")
public class DecisionController {

  @Autowired
  private BookServiceRepository bookServiceRepository;

  @Autowired
  private WorkerRepository workerRepository;

  @Autowired
  private UserRepository userRepository; // Add this

  @Autowired
  private EmailService emailService; // Add this

  @PostMapping("/accepted/{bookedServiceId}/{workerId}")
  public ResponseEntity<?> acceptedService(
    @PathVariable Integer bookedServiceId,
    @PathVariable Integer workerId
  ) {
    try {
      // Validate inputs
      if (bookedServiceId == null || workerId == null) {
        return ResponseEntity.badRequest()
          .body("Please provide bookedServiceId and workerId");
      }

      // 1. Find the booking
      Optional<BookService> bookService = bookServiceRepository.findById(
        bookedServiceId
      );
      if (bookService.isEmpty()) {
        return ResponseEntity.badRequest().body("Service not found.");
      }
      BookService booking = bookService.get();

      // 2. Get the user who made the booking
      Integer userId = booking.getUserId();
      Optional<User> userOptional = userRepository.findById(userId);
      if (userOptional.isEmpty()) {
        return ResponseEntity.badRequest()
          .body("User not found for this booking.");
      }
      User user = userOptional.get();

      // 3. Find and assign worker
      Optional<Worker> optionalWorker = workerRepository.findById(workerId);
      if (optionalWorker.isEmpty()) {
        return ResponseEntity.badRequest().body("Worker not found.");
      }
      Worker worker = optionalWorker.get();

      // 4. Update booking status and assign worker
      booking.setStatus(BookService.Status.ASSIGNWORKER);
      booking.setWorkerId(workerId);
      bookServiceRepository.save(booking);

      // 5. Update worker's assignment
      worker.setAssignWorkId(bookedServiceId);
      workerRepository.save(worker); // Don't forget to save!

      // 6. Send email to user
      sendWorkerAssignmentEmailHtml(user, worker, booking);

      return ResponseEntity.ok(
        "Service has been accepted and user notified successfully."
      );
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError()
        .body("Error processing request: " + e.getMessage());
    }
  }

  // Email sending method
  private void sendWorkerAssignmentEmailHtml(
    User user,
    Worker worker,
    BookService booking
  ) {
    try {
      String to = worker.getWorkerEmail();
      String subject = "Worker Assigned to Your Service Request";

      String htmlContent = String.format(
        """
        <!DOCTYPE html>
        <html>
        <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
            <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 5px;">
                <h2 style="color: #4CAF50;">Worker Assigned!</h2>
                <p>Dear <strong>%s</strong>,</p>
                <p>Great news! A worker has been assigned to your service request.</p>

                <div style="background: #f9f9f9; padding: 15px; margin: 20px 0; border-left: 4px solid #4CAF50;">
                    <h3 style="margin-top: 0;">Assignment Details</h3>
                    <p><strong>Service Booking ID:</strong> %d</p>
                    <p><strong>Assigned Worker:</strong> %s</p>
                    <p><strong>Worker Contact:</strong> %s</p>
                    <p><strong>Worker Email:</strong> %s</p>
                    <p><strong>Scheduled Time:</strong> %s</p>
                    <p><strong>Status:</strong> <span style="color: #4CAF50;">%s</span></p>
                </div>

                <p>The assigned worker will contact you shortly to coordinate the service details.</p>

                <div style="margin: 25px 0; padding: 15px; background: #e8f5e9; border-radius: 5px;">
                    <p style="margin: 0;"><strong>Next Steps:</strong></p>
                    <ol style="margin: 10px 0 0 0;">
                        <li>Expect a call/email from the worker within 24 hours</li>
                        <li>Discuss service details and final timing</li>
                        <li>Be available at the scheduled time</li>
                    </ol>
                </div>

                <p>Thank you for choosing our service!</p>

                <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">

                <p style="color: #666; font-size: 12px;">
                    This is an automated email. Please do not reply directly.
                </p>
            </div>
        </body>
        </html>
        """,
        user.getName(),
        booking.getId(),
        worker.getWorkerName(),
        worker.getWorkerNumber() != null
          ? worker.getWorkerNumber()
          : "To be provided",
        worker.getWorkerEmail(),
        booking.getTime(),
        booking.getStatus()
      );

      emailService.sendHtmlEmail(to, subject, htmlContent);
      System.out.println("HTML assignment email sent to: " + to);
    } catch (Exception e) {
      System.err.println("Failed to send HTML email: " + e.getMessage());
    }
  }

  @PostMapping("/acceptedByWorker/{userid}/{workerid}")
  public ResponseEntity<?> acceptedByWorker(
    @PathVariable Integer userid,
    @PathVariable Integer workerid
  ) {
    try {
      // Validate inputs
      if (userid == null || workerid == null) {
        return ResponseEntity.badRequest().body("Required fields are needed");
      }

      // 1. Find user
      Optional<User> optionalUser = userRepository.findById(userid);
      if (optionalUser.isEmpty()) {
        return ResponseEntity.badRequest().body("User not found.");
      }
      User user = optionalUser.get();

      // 2. Find worker
      Optional<Worker> optionalWorker = workerRepository.findById(workerid);
      if (optionalWorker.isEmpty()) {
        return ResponseEntity.badRequest().body("Worker not found");
      }
      Worker worker = optionalWorker.get();

      // 3. Find booked service from worker's assignment
      Integer assignedWorkId = worker.getAssignWorkId();
      if (assignedWorkId == null || assignedWorkId <= 0) {
        return ResponseEntity.badRequest().body("Worker has no assigned work.");
      }

      Optional<BookService> optionalBookService =
        bookServiceRepository.findById(assignedWorkId);
      if (optionalBookService.isEmpty()) {
        return ResponseEntity.badRequest().body("Booked Service not found.");
      }
      BookService bookService = optionalBookService.get();

      // 4. Verify this service belongs to the user

      // 5. Update status to SUCCESS
      bookService.setStatus(BookService.Status.SUCCESS);
      bookServiceRepository.save(bookService);

      // 6. Send email to user
      sendWorkerArrivalEmailHtml(user, worker, bookService);

      return ResponseEntity.ok(
        "Worker has arrived on time and user has been notified."
      );
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError()
        .body("Error processing request: " + e.getMessage());
    }
  }

  private void sendWorkerArrivalEmailHtml(
    User user,
    Worker worker,
    BookService booking
  ) {
    try {
      String to = user.getEmail();
      String subject = "✅ Worker Has Arrived - Service Confirmation";

      String htmlContent = String.format(
        """
        <!DOCTYPE html>
        <html>
        <head>
            <style>
                body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                .container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px; }
                .header { color: #4CAF50; border-bottom: 2px solid #4CAF50; padding-bottom: 10px; }
                .details { background: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0; }
                .success-badge { background: #4CAF50; color: white; padding: 5px 10px; border-radius: 3px; font-size: 12px; }
                .worker-info { background: #e8f5e9; padding: 15px; border-radius: 5px; margin: 15px 0; }
            </style>
        </head>
        <body>
            <div class="container">
                <h2 class="header">Worker Has Arrived!</h2>

                <p>Dear <strong>%s</strong>,</p>
                <p>Great news! Your assigned worker has arrived at the scheduled location and is ready to begin your service.</p>

                <div class="details">
                    <h3 style="margin-top: 0;">Service Confirmation</h3>
                    <p><strong>Service ID:</strong> #%d</p>
                    <p><strong>Scheduled Time:</strong> %s</p>
                    <p><strong>Status:</strong> <span class="success-badge">%s - ON TIME</span></p>
                </div>

                <div class="worker-info">
                    <h4>Your Service Provider</h4>
                    <p><strong>Name:</strong> %s</p>
                    <p><strong>Contact:</strong> %s</p>
                    <p><strong>Email:</strong> %s</p>
                </div>

                <div style="background: #fff3cd; padding: 15px; border-radius: 5px; margin: 20px 0;">
                    <p style="margin: 0;"><strong>📋 What happens next?</strong></p>
                    <ul style="margin: 10px 0 0 20px;">
                        <li>The worker will introduce themselves</li>
                        <li>Service will commence shortly</li>
                        <li>You can discuss any specific requirements</li>
                        <li>Service completion notification will be sent</li>
                    </ul>
                </div>

                <p>If you have any concerns or need to modify the service, please contact the worker directly.</p>

                <p>Thank you for choosing our service!</p>

                <hr style="border: none; border-top: 1px solid #eee; margin: 25px 0;">

                <p style="color: #666; font-size: 12px; text-align: center;">
                    This is an automated confirmation. Please contact support for urgent issues.
                </p>
            </div>
        </body>
        </html>
        """,
        user.getName(),
        booking.getId(),
        booking.getTime(),
        booking.getStatus(),
        worker.getWorkerName(),
        worker.getWorkerNumber() != null
          ? worker.getWorkerNumber()
          : "Available in app",
        worker.getWorkerEmail()
      );

      emailService.sendHtmlEmail(to, subject, htmlContent);
      System.out.println("HTML arrival email sent to: " + to);
    } catch (Exception e) {
      System.err.println("Failed to send HTML email: " + e.getMessage());
    }
  }
}
