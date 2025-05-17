package org.example.user_service.config.redis;

import org.example.user_service.publisher.EventPublisher;
import org.example.user_service.publisher.subscription.SubscribedEventPublisher;
import org.example.user_service.publisher.subscription.UnsubscribedEventPublisher;
import org.example.user_service.publisher.user.UserRetrievedPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class PublisherConfig {

  @Bean
  public EventPublisher<?> userSubscriptionPublisher(RedisTemplate<String, Object> redisTemplate,
                                                  @Value("${spring.data.redis.channels.subscription.subscribed.name}") String channelName) {
    return new SubscribedEventPublisher(redisTemplate, channelName);
  }

  @Bean
  public EventPublisher<?> userUnsubscriptionPublisher(RedisTemplate<String, Object> redisTemplate,
                                                                   @Value("${spring.data.redis.channels.subscription.unsubscribed.name}") String channelName) {
    return new UnsubscribedEventPublisher(redisTemplate, channelName);
  }

  @Bean
  public EventPublisher<?> userRetrievedPublisher(RedisTemplate<String, Object> redisTemplate,
                                                                     @Value("${spring.data.redis.channels.user.user_retrieved.name}") String channelName) {
    return new UserRetrievedPublisher(redisTemplate, channelName);
  }
}
