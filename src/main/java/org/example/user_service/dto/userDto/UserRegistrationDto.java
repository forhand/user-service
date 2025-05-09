package org.example.user_service.dto.userDto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
  @Column(name = "username", length = 64, nullable = false, unique = true)
  private String username;
  @Column(name = "password", length = 128, nullable = false)
  private String password;
  @Column(name = "email", length = 64, nullable = false, unique = true)
  private String email;
}


