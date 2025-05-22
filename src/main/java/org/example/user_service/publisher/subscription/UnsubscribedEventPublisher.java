package org.example.user_service.publisher.subscription;

import org.example.user_service.dto.event.subscription.UnsubscribedEvent;
import org.example.user_service.publisher.AbstractEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;

public class UnsubscribedEventPublisher extends AbstractEventPublisher<UnsubscribedEvent> {

  public UnsubscribedEventPublisher(RedisTemplate<String, Object> redisTemplate, String channel) {
    super(redisTemplate, channel, UnsubscribedEvent.class);
  }
}
