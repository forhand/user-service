package org.example.user_service.util.container;

import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.entity.User;

public class UserDataContainer {

  private long userId;
  private String userName;
  private final String password;
  private String userEmail;
  private boolean active;

  public UserDataContainer(){
    long id = 0L;
    userId = ++id;
    userName = "userName";
    userEmail = "email%d@domain.com".formatted(++id);
    active = true;
    password = "password";
  }

  public User getUser(){
    return User.builder()
            .id(userId)
            .username(userName)
            .password(password)
            .email(userEmail)
            .active(active)
            .build();
  }

  public UserDto getUserDto(){
    return UserDto.builder()
            .id(userId)
            .username(userName)
            .password(password)
            .email(userEmail)
            .active(active)
            .build();
  }


}
