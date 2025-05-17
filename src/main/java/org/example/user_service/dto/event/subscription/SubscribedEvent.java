package org.example.user_service.dto.event.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubscribedEvent {
  private Long followerId;
  private Long followeeId;
  private LocalDateTime eventAt;
}
