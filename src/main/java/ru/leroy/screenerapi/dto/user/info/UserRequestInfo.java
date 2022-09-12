package ru.leroy.screenerapi.dto.user.info;

import javax.persistence.Id;
import lombok.Data;

/**
* DTO for request by an id.
*/
@Data
public class UserRequestInfo {

  @Id
  private Long id;

}
