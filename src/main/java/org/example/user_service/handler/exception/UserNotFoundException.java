package org.example.user_service.handler.exception;

public class UserNotFoundException extends RuntimeException{
  public UserNotFoundException(long userId) {
    super("User with id: %d not found".formatted(userId));
  }
}
