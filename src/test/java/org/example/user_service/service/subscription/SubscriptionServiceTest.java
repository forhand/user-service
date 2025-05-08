package org.example.user_service.service.subscription;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {
  @Mock
  private SubscriptionRepository subscriptionRepository;
  @Mock
  private UserService userService;
  @Mock
  private MessageSource messageSource;
  @Mock
  private SubscriptionDataValidator validator;
  @InjectMocks
  private SubscriptionService service;
  private SubscriptionDataContainer subscriptionDataContainer;


  @BeforeEach
  void setUp() {
    subscriptionDataContainer = new SubscriptionDataContainer();
  }

  @Test
  void testFollowUser_successful() {
    assertDoesNotThrow(() -> service.followUser(1L, 2L));
  }

  @Test
  void testFollowUser_throwsDataValidationException() {
    doThrow(new DataValidationException("Invalid input")).when(validator).validateFollowUser(anyLong(), anyLong());

    assertThrows(DataValidationException.class, () -> service.followUser(1L, 2L));
  }

  @Test
  void testFollowUser_throwsResourceNotFoundException() {
    doThrow(new ResourceNotFoundException("User not found")).when(validator).validateFollowUser(anyLong(), anyLong());

    assertThrows(ResourceNotFoundException.class, () -> service.followUser(1L, 2L));
  }

  @Test
  void testUnfollowUser() {
    assertDoesNotThrow(() -> service.unfollowUser(1L, 2L));
  }

  @Test
  void testUnfollowUser_throwsDataValidationException() {
    doThrow(new DataValidationException("Invalid input")).when(validator).validateUnfollowUser(anyLong(), anyLong());

    assertThrows(DataValidationException.class, () -> service.unfollowUser(1L, 2L));
  }

  @Test
  void testUnfollowUser_throwsResourceNotFoundException() {
    doThrow(new ResourceNotFoundException("User not found")).when(validator).validateFollowUser(anyLong(), anyLong());

    assertThrows(ResourceNotFoundException.class, () -> service.followUser(1L, 2L));
  }

  @Test
  void testGetFollowerCount() {
    int expectedCount = 10;
    when(subscriptionRepository.findFolloweesAmountByFollowerId(anyLong())).thenReturn(expectedCount);

    int actualCount = service.getFollowerCount(1L);

    assertEquals(expectedCount, actualCount);
  }

  @Test
  void testGetFollowerCount_userNotFound() {
    doThrow(new ResourceNotFoundException("User not found")).when(validator).validateUserExists(anyLong());

    assertThrows(ResourceNotFoundException.class, () -> service.getFollowerCount(1L));
  }
}