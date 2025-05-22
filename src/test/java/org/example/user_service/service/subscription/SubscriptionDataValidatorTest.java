package org.example.user_service.service.subscription;

import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.handler.exception.DataValidationException;
import org.example.user_service.handler.exception.ResourceNotFoundException;
import org.example.user_service.repository.SubscriptionRepository;
import org.example.user_service.service.UserService;
import org.example.user_service.util.container.SubscriptionDataContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionDataValidatorTest {
  @Mock
  private SubscriptionRepository subscriptionRepository;
  @Mock
  private UserService userService;
  @Mock
  private MessageSource messageSource;
  @InjectMocks
  private SubscriptionDataValidator validator;
  private SubscriptionDataContainer container;
  private long followerId;
  private long followeeId;

  @BeforeEach
  void setUp() {
    container = new SubscriptionDataContainer();
    followerId = 1L;
    followeeId = 2L;
  }

  @Test
  void testValidateFollowUser_success() {
    when(subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)).thenReturn(false);

    assertDoesNotThrow(() -> validator.validateFollowUser(followerId, followeeId));
  }

  @Test
  void testValidateFollowUser_SubscribingSelf() {
    followerId = followeeId;

    assertThrows(DataValidationException.class, () -> validator.validateFollowUser(followerId, followeeId));
  }

  @Test
  void testValidateFollowUser_SubscriptionExist() {
    when(subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)).thenReturn(true);
    when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Error");

    assertThrows(DataValidationException.class, () -> validator.validateFollowUser(followerId, followeeId));
  }

  @Test
  void testValidateFollowUser_FollowerNotExist() {
    doThrow(new ResourceNotFoundException("User not found")).when(userService).validateUserExists(followerId);

    assertThrows(ResourceNotFoundException.class, () -> validator.validateFollowUser(followerId, followeeId));
  }

  @Test
  void testValidateFollowUser_FolloweeNotExist() {
    doNothing().when(userService).validateUserExists(followerId);
    doThrow(new ResourceNotFoundException("User not found")).when(userService).validateUserExists(followeeId);

    assertThrows(ResourceNotFoundException.class, () -> validator.validateFollowUser(followerId, followeeId));
  }

  @Test
  void testValidateUnfollowUser_success() {
    when(subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)).thenReturn(true);

    assertDoesNotThrow(() -> validator.validateUnfollowUser(followerId, followeeId));
  }

  @Test
  void testValidateUnFollowUser_SubscribingSelf() {
    followerId = followeeId;

    assertThrows(DataValidationException.class, () -> validator.validateUnfollowUser(followerId, followeeId));
  }

  @Test
  void testValidateUnFollowUser_SubscriptionExist() {
    when(subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)).thenReturn(false);
    when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Error");

    assertThrows(DataValidationException.class, () -> validator.validateUnfollowUser(followerId, followeeId));
  }

  @Test
  void testValidateUnFollowUser_FollowerNotExist() {
    doThrow(new ResourceNotFoundException("User not found")).when(userService).validateUserExists(followerId);

    assertThrows(ResourceNotFoundException.class, () -> validator.validateUnfollowUser(followerId, followeeId));
  }

  @Test
  void testValidateUnFollowUser_FolloweeNotExist() {

    doNothing().when(userService).validateUserExists(followerId);
    doThrow(new ResourceNotFoundException("User not found")).when(userService).validateUserExists(followeeId);

    assertThrows(ResourceNotFoundException.class, () -> validator.validateUnfollowUser(followerId, followeeId));
  }

  @Test
  void validateUserExists_success() {
    assertDoesNotThrow(() -> validator.validateUserExists(followerId));
  }

  @Test
  void validateUserExists_throwResourceNotFount() {
    doThrow(new ResourceNotFoundException("User not found"))
        .when(userService)
        .validateUserExists(followerId);

    assertThrows(ResourceNotFoundException.class, () -> validator.validateUserExists(followerId));
  }
}