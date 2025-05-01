package org.example.user_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.dto.userDto.UserRegistrationDto;
import org.example.user_service.handler.exception.DataValidationException;
import org.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class UserController {

  private final String skipEventHeader;
  private final UserService userService;

  @Autowired
  public UserController(@Value("${client.skip_event.header}") String skipEventHeader,
                        UserService userService) {
    this.skipEventHeader = skipEventHeader;
    this.userService = userService;
  }

  @GetMapping("/{user_id}")
  public UserDto getUser(@PathVariable("user_id") long userId, HttpServletRequest request) {
    if (userId <= 0) {
      throw new DataValidationException("User id must be positive");
    }
    boolean skipEvent = getSkipEvent(request);
    return userService.getUser(userId, skipEvent);
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

  private boolean getSkipEvent(HttpServletRequest request) {
    return "true".equals(request.getHeader(skipEventHeader));
  }


  // todo: реализовать сервис получения пользователей с премиум подпиской
}
