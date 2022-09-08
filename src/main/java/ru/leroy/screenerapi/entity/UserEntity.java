package ru.leroy.screenerapi.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_email", nullable = false, length = 45)
    private String email;

    @Column(name = "user_password", nullable = false, length = 45)
    private String password;

    @Column(name = "rate", length = 45)
    private String rate;

    @Column(name = "expiration")
    private Instant expiration;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (null == obj || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;
        final UserEntity that = (UserEntity) obj;
        return null != this.id && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
