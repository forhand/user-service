package org.example.user_service.service.subscription;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.user_service.handler.exception.DataValidationException;
import org.example.user_service.repository.SubscriptionRepository;
import org.example.user_service.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionDataValidator {
  private final SubscriptionRepository subscriptionRepository;
  private final UserService userService;
  private final MessageSource messageSource;

  public void validateFollowUser(long followerId, long followeeId) {
    checkSubscribingSelf(followerId, followeeId);
    userService.validateUserExists(followerId);
    userService.validateUserExists(followeeId);
    checkSubscription(followerId, followeeId);
  }

  private void checkSubscription(long followerId, long followeeId) {
    if (subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
      throw new DataValidationException(
              messageSource.getMessage("validation.subscription.already.exists", null, null)
      );
    }
  }

  private void checkSubscribingSelf(long followerId, long followeeId) {
    if(Objects.equals(followerId, followeeId)) {
      throw new DataValidationException(messageSource.getMessage("validation.subscribe.self.error", null, null));
    }
  }
}
