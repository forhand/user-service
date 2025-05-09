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

  private Long id;
  @NotBlank
  private String username;
  @NotBlank
  private String email;
  private boolean active;
  private List<Long> followerIds;
  private List<Long> followeeIds;
}
