package org.example.user_service.service;

import org.example.user_service.config.context.UserContext;
import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.dto.userDto.UserRegistrationDto;
import org.example.user_service.entity.User;
import org.example.user_service.handler.exception.ResourceNotFoundException;
import org.example.user_service.mapper.UserMapperImpl;
import org.example.user_service.repository.UserRepository;
import org.example.user_service.service.encoder.PasswordEncoder;
import org.example.user_service.util.container.UserDataContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
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
  @Mock
  private PasswordEncoder passwordEncoder;
  @Spy
  private UserMapperImpl userMapper;
  @Mock
  private EventPublisherService eventPublisherUtil;
  @Mock
  private UserContext userContext;
  @InjectMocks
  private UserService userService;
  @Captor
  private ArgumentCaptor<User> captor;
  private UserDataContainer userDataContainer;
  private User userEntity;
  private UserDto userDto;


  @BeforeEach
  void setUp() {
    captor = ArgumentCaptor.forClass(User.class);
    userDataContainer = new UserDataContainer();
    userDto = userDataContainer.getUserDto();
    userEntity = userDataContainer.getUser();
  }

  @Test
  void testGetUserNotExist() {
    long invalidUserId = -1;
    when(userRepository.findById(invalidUserId)).thenThrow(new ResourceNotFoundException("User not found"));

    assertThrows(ResourceNotFoundException.class, () -> userService.getUser(invalidUserId, false));
  }

  @Test
  void testGetUser() {
    long validUserId = userEntity.getId();
    when(userRepository.findById(validUserId)).thenReturn(Optional.ofNullable(userEntity));
    when((userContext.getUserId())).thenReturn(validUserId);

    UserDto actUserDto = userService.getUser(validUserId, false);

    assertEquals(validUserId, actUserDto.getId());
    verify(eventPublisherUtil, times(1)).publishEvent(any());
  }

  @Test
  void testGetUserWOPublishEvent() {
    long validUserId = userEntity.getId();
    when(userRepository.findById(validUserId)).thenReturn(Optional.ofNullable(userEntity));

    UserDto actUserDto = userService.getUser(validUserId, true);

    assertEquals(validUserId, actUserDto.getId());
    verify(eventPublisherUtil, times(0)).publishEvent(any());
  }

  @Test
  void testCreateUser() {
    UserRegistrationDto userRegistrationDto = userDataContainer.getUserRegistrationDto();
    String password = userRegistrationDto.getPassword();
    String encodedPassword = password + "_encoded";
    when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
    User user = userMapper.toEntity(userRegistrationDto);
    user.setPassword(encodedPassword);
    User savedUser = userMapper.toEntity(userRegistrationDto);
    savedUser.setPassword(encodedPassword);
    savedUser.setId(1L);
    when(userRepository.save(user)).thenReturn(savedUser);

    UserDto actUserDto = userService.createUser(userRegistrationDto);

    verify(passwordEncoder, times(1)).encode(anyString());
    verify(userRepository).save(captor.capture());
    User actUserToSave = captor.getValue();
    assertEquals(user, actUserToSave);
    assertEquals(userMapper.toDto(savedUser), actUserDto);
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
    assertEquals(userDto.isActive(), Boolean.FALSE);
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
    assertEquals(userDto.isActive(), Boolean.FALSE);
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