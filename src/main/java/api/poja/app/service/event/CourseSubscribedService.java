package api.poja.app.service.event;

import api.poja.app.endpoint.event.model.CourseSubscribed;
import api.poja.app.mail.Email;
import api.poja.app.mail.Mailer;
import api.poja.app.repository.CourseRepository;
import api.poja.app.repository.UserRepository;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CourseSubscribedService implements Consumer<CourseSubscribed> {
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final Mailer mailer;

  @Override
  @SneakyThrows
  @Transactional
  public void accept(CourseSubscribed event) {
    var user = userRepository.findById(event.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found: " + event.getUserId()));
    var course = courseRepository.findById(event.getCourseId())
        .orElseThrow(() -> new RuntimeException("Course not found: " + event.getCourseId()));

    if (!user.getCourses().contains(course)) {
      user.getCourses().add(course);
      userRepository.save(user);
    }

    var recipient = new InternetAddress(user.getEmail());
    mailer.accept(new Email(
        recipient,
        List.of(),
        List.of(),
        "Confirmation d'inscription",
        "<h1>Inscription confirmée</h1><p>Vous êtes inscrit au cours : <b>" + course.getTitle() + "</b></p>",
        List.of()));
  }
}
