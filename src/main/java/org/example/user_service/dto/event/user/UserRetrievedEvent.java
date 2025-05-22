package org.example.user_service.dto.event.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.user_service.dto.event.Event;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserRetrievedEvent extends Event {
  private long userId;
  private long actorId;
  private LocalDateTime eventAt;
}
