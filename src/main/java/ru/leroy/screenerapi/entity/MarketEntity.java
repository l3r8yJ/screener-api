package ru.leroy.screenerapi.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

/**
 * The  Market entity.
 */

@Entity
@Table(name = "markets")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MarketEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "short_name", nullable = false)
  private String shortName;

  @Column(name = "description")
  private String description;

  @Column(name = "picture", nullable = false)
  private String picture;

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (null == obj || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
      return false;
    }
    final MarketEntity that = (MarketEntity) obj;
    return null != this.id && Objects.equals(this.id, that.id);
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }
}

