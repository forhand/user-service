package org.example.user_service.service.subscription;

import lombok.RequiredArgsConstructor;
import org.example.user_service.dto.event.subscription.SubscribedEvent;
import org.example.user_service.dto.event.subscription.UnsubscribedEvent;
import org.example.user_service.publisher.subscription.SubscribedEventPublisher;
import org.example.user_service.publisher.subscription.UnsubscribedEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionEventPublisherService {
  private final SubscribedEventPublisher userSubscriptionPublisher;
  private final UnsubscribedEventPublisher userUnsubscriptionPublisher;

  public void publishUserSubscriptionEvent(SubscribedEvent event) {
    userSubscriptionPublisher.publish(event);
  }

  public void publishUserUnsubscriptionEvent(UnsubscribedEvent event) {
    userUnsubscriptionPublisher.publish(event);
  }
}
