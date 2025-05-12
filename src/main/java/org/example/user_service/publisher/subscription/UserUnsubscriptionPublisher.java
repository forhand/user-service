package org.example.user_service.publisher.subscription;

import org.example.user_service.dto.event.subscription.UserUnsubscriptionEvent;
import org.example.user_service.publisher.AbstractEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;

public class UserUnsubscriptionPublisher extends AbstractEventPublisher<UserUnsubscriptionEvent> {

  public UserUnsubscriptionPublisher(RedisTemplate<String, Object> redisTemplate, String channel) {
    super(redisTemplate, channel, UserUnsubscriptionEvent.class);
  }
}
