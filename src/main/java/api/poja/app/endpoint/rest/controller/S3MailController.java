package api.poja.app.endpoint.rest.controller;

import api.poja.app.mail.Email;
import api.poja.app.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class S3MailController {

  private final Mailer mailer;

  @PostMapping("/email-with-pdf")
  public ResponseEntity<String> sendEmailWithPdf(@RequestParam String to) {

    try {
      var recipient = new InternetAddress(to);
      var pdf = new ClassPathResource("pdfs/cours.pdf").getFile();

      mailer.accept(new Email(
          recipient,
          List.of(),
          List.of(),
          "Test email with PDF",
          "<h1>PDF joint</h1><p>Cours : <b>Spring Boot 101</b></p>",
          List.of(pdf)));

      return ResponseEntity.ok("Email sent to " + to);
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body("ERREUR: " + e.getClass().getSimpleName() + " - " + e.getMessage());
    }
  }
}
