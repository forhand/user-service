package org.example.user_service.util.container;

import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.dto.userDto.UserRegistrationDto;
import org.example.user_service.entity.User;
import org.example.user_service.entity.contact.ContactPreference;
import org.example.user_service.entity.contact.PreferredContact;

import java.util.ArrayList;
import java.util.List;

public class UserDataContainer {

  long id = 0L;
  private long userId;
  private String userName;
  private final String password;
  private String userEmail;
  private int age;
  private ContactPreference contactPreference;
  private boolean active;
  private List<User> followers;
  private List<User> followees;
  private PreferredContact preferredContact;


  public UserDataContainer() {
    userId = ++id;
    userName = "userName";
    userEmail = "email%d@domain.com".formatted(++id);
    password = "password";
    age = 18;
   preferredContact = PreferredContact.EMAIL;
    active = true;
    followers = getUsers();
    followees = getUsers();
  }

  public User getUser() {
    return User.builder()
            .id(userId)
            .username(userName)
            .password(password)
            .email(userEmail)
            .age(age)
            .contactPreference(new ContactPreference(++id, new User(), preferredContact))
            .active(active)
            .followers(followers)
            .followees(followees)
            .build();
  }

  public UserDto getUserDto() {
    return UserDto.builder()
            .id(userId)
            .username(userName)
            .email(userEmail)
            .age(age)
            .active(active)
            .preferredContact(preferredContact)
            .followerIds(getUserIds(followers))
            .followeeIds(getUserIds(followees))
            .build();
  }

  public UserRegistrationDto getUserRegistrationDto() {
    return UserRegistrationDto.builder()
            .username(userName)
            .password(password)
            .email(userEmail)
            .age(age)
            .build();
  }

  private List<Long> getUserIds(List<User> followers) {
    return followers.stream().map(User::getId).toList();
  }

  private List<User> getUsers() {
    return new ArrayList<>(List.of(createNewUser(), createNewUser()));
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
