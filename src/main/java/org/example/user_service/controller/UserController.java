package org.example.user_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.user_service.dto.userDto.UserRegistrationDto;
import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.handler.exception.DataValidationException;
import org.example.user_service.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/{user_id}")
  public UserDto getUser(@PathVariable("user_id") long userId) {
    if (userId <= 0) {
      throw new DataValidationException("User id must be positive");
    }
    return userService.getUser(userId);
  }

  @PostMapping("/register")
  public UserDto createUser(@RequestBody @Valid UserRegistrationDto user) {
      return userService.createUser(user);
  }

  @PutMapping("/{user_id}/deactivate")
  public UserDto deactivatesUserProfile(@PathVariable("user_id") @Positive Long userId) {
    return userService.deactivateUserProfile(userId);
  }

  @GetMapping("/list")
  public List<UserDto> getUsersByIds(@RequestParam("userId") List<Long> userIds) {
    return userService.getUsersByIds(userIds);
  }

  // todo: реализовать сервис получения пользователей с премиум подпиской

}
