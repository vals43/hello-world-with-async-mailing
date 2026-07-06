package api.poja.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

  @Id private UUID id;

  @Column(nullable = false)
  private String title;

  @Column(name = "start_instant", nullable = false)
  private Instant start;

  @Column(name = "end_instant", nullable = false)
  private Instant end;

  @ManyToMany(mappedBy = "courses")
  private List<User> users;
}
