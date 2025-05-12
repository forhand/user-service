package org.example.user_service.config.redis;

import org.example.user_service.publisher.EventPublisher;
import org.example.user_service.publisher.subscription.UserSubscriptionPublisher;
import org.example.user_service.publisher.subscription.UserUnsubscriptionPublisher;
import org.example.user_service.publisher.user.UserRetrievedPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class PublisherConfig {

  @Bean
  public EventPublisher<?> userSubscriptionPublisher(RedisTemplate<String, Object> redisTemplate,
                                                  @Value("${spring.data.redis.channels.subscription.user_subscription.name}") String channelName) {
    return new UserSubscriptionPublisher(redisTemplate, channelName);
  }

  @Bean
  public EventPublisher<?> userUnsubscriptionPublisher(RedisTemplate<String, Object> redisTemplate,
                                                                   @Value("${spring.data.redis.channels.subscription.user_unsubscription.name}") String channelName) {
    return new UserUnsubscriptionPublisher(redisTemplate, channelName);
  }

  @Bean
  public EventPublisher<?> userRetrievedPublisher(RedisTemplate<String, Object> redisTemplate,
                                                                     @Value("${spring.data.redis.channels.user.user_retrieved.name}") String channelName) {
    return new UserRetrievedPublisher(redisTemplate, channelName);
  }
}
