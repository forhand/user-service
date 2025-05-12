package org.example.user_service.dto.event.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSubscriptionEvent {
  private Long followerId;
  private Long followeeId;
}
