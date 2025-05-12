package org.example.user_service.publisher.subscription;

import org.example.user_service.dto.event.subscription.UserSubscriptionEvent;
import org.example.user_service.publisher.AbstractEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;

public class UserSubscriptionPublisher extends AbstractEventPublisher<UserSubscriptionEvent> {

  public UserSubscriptionPublisher(RedisTemplate<String, Object> redisTemplate, String channel) {
    super(redisTemplate, channel, UserSubscriptionEvent.class);
  }
}
