package api.poja.app.endpoint.rest.controller;

import api.poja.app.entity.Course;
import api.poja.app.entity.User;
import api.poja.app.mail.Email;
import api.poja.app.mail.Mailer;
import api.poja.app.repository.CourseRepository;
import api.poja.app.repository.UserRepository;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DebugController {
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final Mailer mailer;

  @GetMapping("/debug/subscribe-sync")
  public ResponseEntity<String> subscribeSync(
      @RequestParam UUID userId, @RequestParam UUID courseId, @RequestParam String email) {
    try {
      var user = userRepository.findById(userId)
          .orElseThrow(() -> new RuntimeException("User not found: " + userId));

      var course = courseRepository.findById(courseId)
          .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

      if (!user.getCourses().contains(course)) {
        user.getCourses().add(course);
        userRepository.save(user);
      }

      var recipient = new InternetAddress(email);
      mailer.accept(new Email(
          recipient,
          List.of(),
          List.of(),
          "Confirmation inscription",
          "<h1>Inscription confirmée</h1><p>Cours : <b>" + course.getTitle() + "</b></p>",
          List.of()));

      return ResponseEntity.ok("OK - Email envoyé à " + email);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("ERREUR: " + e.getClass().getSimpleName() + " - " + e.getMessage());
    }
  }
}
