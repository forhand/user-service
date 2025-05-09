package org.example.user_service.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilterDto {
  private String username;
  private Integer age;
  private Integer minAge;
  private Integer maxAge;
}
