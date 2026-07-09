package api.poja.app.endpoint.rest.controller;

import api.poja.app.file.bucket.BucketComponent;
import api.poja.app.mail.Email;
import api.poja.app.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class S3MailController {

  private final BucketComponent bucketComponent;
  private final Mailer mailer;

  @PostMapping("/email-with-pdf")
  public ResponseEntity<String> sendEmailWithPdf(
      @RequestParam String to,
      @RequestParam(defaultValue = "pdfs/cours.pdf") String bucketKey) {

    try {
      var recipient = new InternetAddress(to);
      var pdf = bucketComponent.download(bucketKey);

      mailer.accept(new Email(
          recipient,
          List.of(),
          List.of(),
          "Test email with PDF from S3",
          "<h1>PDF joint depuis S3</h1><p>Clé : <b>" + bucketKey + "</b></p>",
          List.of(pdf)));

      return ResponseEntity.ok("Email sent to " + to + " (key: " + bucketKey + ")");
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body("ERREUR: " + e.getClass().getSimpleName() + " - " + e.getMessage());
    }
  }
}
