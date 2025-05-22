package org.example.user_service.dto.subcription;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowRequest {

  @NotNull
  @Positive(message = "FollowerId must be positive")
  private Long followerId;
  @NotNull
  @Positive(message = "FolloweeId must be positive")
  private Long followeeId;
}
