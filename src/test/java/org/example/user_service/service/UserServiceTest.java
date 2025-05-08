package org.example.user_service.service;

import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.entity.User;
import org.example.user_service.handler.exception.ResourceNotFoundException;
import org.example.user_service.mapper.UserMapperImpl;
import org.example.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private MessageSource messageSource;
  @Spy
  private UserMapperImpl userMapper;
  @InjectMocks
  private UserService userService;

  @Test
  void testGetUserNotExist() {
    long invalidUserId = -1;
    when(userRepository.findById(invalidUserId)).thenThrow(new ResourceNotFoundException("User not found"));

    assertThrows(ResourceNotFoundException.class, () -> userService.getUser(invalidUserId));
  }

  @Test
  void testGetUser() {
    long validUserId = 1;
    User user = User.builder()
            .id(validUserId)
            .build();
    when(userRepository.findById(validUserId)).thenReturn(Optional.ofNullable(user));

    UserDto actUserDto = userService.getUser(validUserId);

    assertEquals(validUserId, actUserDto.getId());
  }

  @Test
  void testCreateUser() {
    String username = "Serg";
    UserDto userDto = UserDto.builder()
            .username(username).build();
    User user = User.builder().username(username).build();
    User expectedUser = User.builder()
            .id(1L)
            .username(username).build();
    when(userRepository.save(user)).thenReturn(expectedUser);

    UserDto resultUserDto = userService.createUser(userDto);

    verify(userRepository).save(any(User.class));
    assertEquals(userDto.getUsername(), resultUserDto.getUsername());
    assertNotSame(userDto, resultUserDto);
  }

  @Test
  public void testCreateUserWithNullArgument() {
    UserDto userDto = null;
    when(userRepository.save(null)).thenThrow(new IllegalArgumentException());

    assertThrows(IllegalArgumentException.class, () -> userService.createUser(userDto));
  }

  @Test
  void testGetUsersByIds() {
    List<Long> userIds = Arrays.asList(1L, 2L, 3L);
    List<User> users = getUsers(userIds);
    when(userRepository.findAllById(userIds)).thenReturn(users);

    List<UserDto> resultUserDtos = userService.getUsersByIds(userIds);

    assertEquals(resultUserDtos.size(), users.size());
  }

  @Test
  void testDeactivationUserProfile() {
    Long userId = 1L;
    User activeUser = User.builder()
            .id(userId)
            .active(Boolean.TRUE).build();
    User deactivatedUser = User.builder()
            .id(userId)
            .active(Boolean.FALSE).build();
    when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(activeUser));
    when(userRepository.save(any(User.class))).thenReturn(deactivatedUser);

    UserDto userDto = userService.deactivateUserProfile(userId);
    verify(userRepository, times(1)).findById(userId);
    verify(userRepository, times(1)).save(deactivatedUser);
    assertEquals(userDto.getId(), userId);
    assertEquals(userDto.getActive(), Boolean.FALSE);
  }

  @Test
  void testDeactivationAlreadyDeactivatedUserProfile() {
    Long userId = 1L;
    User deactivatedUser = User.builder()
            .id(userId)
            .active(Boolean.FALSE).build();
    when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(deactivatedUser));

    UserDto userDto = userService.deactivateUserProfile(userId);
    verify(userRepository, times(1)).findById(userId);
    verify(userRepository, times(0)).save(deactivatedUser);
    assertEquals(userDto.getId(), userId);
    assertEquals(userDto.getActive(), Boolean.FALSE);
  }

  @Test
  void validateUserExists() {
    when(userRepository.existsById(any(Long.class))).thenReturn(true);

    assertDoesNotThrow(() -> userService.validateUserExists(1L));
  }

  @Test
  void validateUserExists_throwsException() {
    when(userRepository.existsById(any(Long.class))).thenReturn(false);
    when(messageSource.getMessage(any(String.class), any(), any())).thenReturn("User not found");

    assertThrows(ResourceNotFoundException.class, () -> userService.validateUserExists(1L));
  }

  private static List<User> getUsers(List<Long> userIds) {
    List<User> users = new ArrayList<>();
    for (Long userId : userIds) {
      users.add(User.builder().id(userId).build());
    }
    return users;
  }
}