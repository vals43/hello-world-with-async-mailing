package api.poja.app.endpoint.rest.controller;

import api.poja.app.endpoint.event.EventProducer;
import api.poja.app.endpoint.event.model.CourseSubscribed;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SubscriptionController {
  private final EventProducer<CourseSubscribed> eventProducer;

  @PostMapping("/users/{userId}/courses/{courseId}/subscribe")
  public ResponseEntity<Void> subscribe(@PathVariable UUID userId, @PathVariable UUID courseId) {
    var event = CourseSubscribed.builder().userId(userId).courseId(courseId).build();
    eventProducer.accept(List.of(event));
    return ResponseEntity.accepted().build();
  }
}
