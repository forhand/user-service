package org.example.user_service.exceptionHendler.exception;

public class UserNotFoundException extends RuntimeException{
  public UserNotFoundException(long userId) {
    super("User with id: %d not found".formatted(userId));
  }
}
