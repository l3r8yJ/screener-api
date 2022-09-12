package ru.leroy.screenerapi.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;

/**
 * The User entity.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {
  @Id
  @NonNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @NonNull
  @Column(name = "user_email", nullable = false, length = 45)
  private String email;

  @NonNull
  @Column(name = "user_password", nullable = false, length = 45)
  private String password;

  @Column(name = "rate", length = 45)
  private String rate;

  @CreatedDate
  @Column(name = "expiration")
  private Instant expiration;

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (null == obj || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
      return false;
    }
    final UserEntity that = (UserEntity) obj;
    return Objects.equals(this.id, that.id);
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }
}
