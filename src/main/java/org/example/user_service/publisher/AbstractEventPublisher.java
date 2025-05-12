package org.example.user_service.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public abstract class AbstractEventPublisher<T> implements EventPublisher<T> {

  private final RedisTemplate<String, Object> redisTemplate;
  private final String channel;
  protected final Class<T> eventType;

  public AbstractEventPublisher(RedisTemplate<String, Object> redisTemplate, String channel, Class<T> eventType) {
    this.redisTemplate = redisTemplate;
    this.channel = channel;
    this.eventType = eventType;
  }

  @Override
  public void publish(T event) {
    log.info("Publishing event: {}", event);
    redisTemplate.convertAndSend(channel, event);
  }

  @Override
  public Class<T> getEventType() {
    return eventType;
  }
}