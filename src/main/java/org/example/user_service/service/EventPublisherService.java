package org.example.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.user_service.dto.event.Event;
import org.example.user_service.publisher.EventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublisherService {

  private final List<EventPublisher<?>> eventPublishers;

  public <E extends Event> void publishEvent(E event) {
    eventPublishers.stream()
            .filter(p -> p.getEventType().isAssignableFrom(event.getClass()))
            .findFirst()
                    .ifPresent(p -> ((EventPublisher<E>)p).publish(event));
  }
}
