package org.example.user_service.publisher.user;

import org.example.user_service.dto.event.user.UserRetrievedEvent;
import org.example.user_service.publisher.AbstractEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;

public class UserRetrievedPublisher extends AbstractEventPublisher<UserRetrievedEvent>{

  public UserRetrievedPublisher(RedisTemplate<String, Object> redisTemplate, String channel) {
    super(redisTemplate, channel, UserRetrievedEvent.class);
  }
}
