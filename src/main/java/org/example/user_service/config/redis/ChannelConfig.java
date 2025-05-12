package org.example.user_service.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class ChannelConfig {

  @Bean
  public ChannelTopic topicUserSubscriptionEvent(
          @Value("${spring.data.redis.channels.subscription.user_subscription.name}") String channelName) {
    return new ChannelTopic(channelName);
  }
}
