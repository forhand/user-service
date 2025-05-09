package org.example.user_service.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

  protected Long id;
  @NotBlank
  protected String username;
  @NotBlank
  protected String email;
  @NotNull
  protected Boolean active;
  protected List<Long> followerIds;
  protected List<Long> followeeIds;
}
