package org.example.user_service.publisher;

public interface EventPublisher <T> {
  void publish(T event);

  Class<T> getEventType();
}
