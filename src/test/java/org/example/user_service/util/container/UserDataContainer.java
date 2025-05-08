package org.example.user_service.util.container;

import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataContainer {

  long id = 0L;
  private long userId;
  private String userName;
  private final String password;
  private String userEmail;
  private boolean active;
  private List<User> followers;
  private List<User> followees;


  public UserDataContainer() {
    userId = ++id;
    userName = "userName";
    userEmail = "email%d@domain.com".formatted(++id);
    active = true;
    password = "password";
    this.followers = getUsers();
    this.followees = getUsers();
  }

  public User getUser() {
    return User.builder()
            .id(userId)
            .username(userName)
            .password(password)
            .email(userEmail)
            .active(active)
            .followers(followers)
            .followees(followees)
            .build();
  }

  public UserDto getUserDto() {
    return UserDto.builder()
            .id(userId)
            .username(userName)
            .password(password)
            .email(userEmail)
            .active(active)
            .followerIds(getUserIds(followers))
            .followeeIds(getUserIds(followees))
            .build();
  }

  private List<Long> getUserIds(List<User> followers) {
    return followers.stream().map(User::getId).toList();
  }

  private List<User> getUsers() {
    return new ArrayList(List.of(createNewUser(), createNewUser()));
  }

  private User createNewUser() {
    return User.builder()
            .id(++id)
            .username(userName)
            .password(password)
            .email("mail%d@domain.com".formatted(++id))
            .active(active)
            .build();
  }
}
