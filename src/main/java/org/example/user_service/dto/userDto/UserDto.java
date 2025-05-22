package org.example.user_service.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.user_service.entity.contact.ContactPreference;
import org.example.user_service.entity.contact.PreferredContact;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

  private Long id;
  @NotBlank
  private String username;
  private int age;
  @NotBlank
  private String email;
  private PreferredContact preferredContact;
  private boolean active;
  private List<Long> followerIds;
  private List<Long> followeeIds;
}
