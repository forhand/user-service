package org.example.user_service.publisher.subscription;

import org.example.user_service.dto.event.subscription.SubscribedEvent;
import org.example.user_service.publisher.AbstractEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;

public class SubscribedEventPublisher extends AbstractEventPublisher<SubscribedEvent> {

  public SubscribedEventPublisher(RedisTemplate<String, Object> redisTemplate, String channel) {
    super(redisTemplate, channel, SubscribedEvent.class);
  }
}
