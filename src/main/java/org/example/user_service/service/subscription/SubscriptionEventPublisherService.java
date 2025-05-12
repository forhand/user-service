package org.example.user_service.service.subscription;

import lombok.RequiredArgsConstructor;
import org.example.user_service.dto.event.subscription.UserSubscriptionEvent;
import org.example.user_service.dto.event.subscription.UserUnsubscriptionEvent;
import org.example.user_service.publisher.subscription.UserSubscriptionPublisher;
import org.example.user_service.publisher.subscription.UserUnsubscriptionPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionEventPublisherService {
  private final UserSubscriptionPublisher userSubscriptionPublisher;
  private final UserUnsubscriptionPublisher userUnsubscriptionPublisher;

  public void publishUserSubscriptionEvent(UserSubscriptionEvent event) {
    userSubscriptionPublisher.publish(event);
  }

  public void publishUserUnsubscriptionEvent(UserUnsubscriptionEvent event) {
    userUnsubscriptionPublisher.publish(event);
  }
}
