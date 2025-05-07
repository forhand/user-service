package org.example.user_service.handler.exception;

public class ResourceNotFoundException extends RuntimeException{
  public ResourceNotFoundException(long userId) {
    super("User with id: %d not found".formatted(userId));
  }
}
