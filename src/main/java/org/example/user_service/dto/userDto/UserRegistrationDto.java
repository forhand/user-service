package org.example.user_service.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

  @NotBlank(message = "Username must not be blank")
  private String username;
  @NotBlank(message = "Password must not be blank")
  private String password;
  @Email(message = "Email must be valid")
  private String email;
  @NotNull
  @Range(min = 0, max = 150, message = "Age must be between 0 and 150")
  private Integer age;
}


