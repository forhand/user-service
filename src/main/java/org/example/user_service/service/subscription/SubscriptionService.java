package org.example.user_service.service.subscription;

import lombok.RequiredArgsConstructor;
import org.example.user_service.repository.SubscriptionRepository;
import org.example.user_service.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;
  private final UserService userService;
  private final MessageSource messageSource;
  private final SubscriptionDataValidator validator;

  public void followUser(long followerId, long followeeId) {
    validator.validateFollowUser(followerId, followeeId);
    subscriptionRepository.followUser(followerId, followeeId);
  }

  public void unfollowUser(long followerId, long followeeId) {
    validator.validateUnfollowUser(followerId, followeeId);
    subscriptionRepository.unfollowUser(followerId, followeeId);
  }

  public int getFollowerCount(long followerId) {
    validator.validateUserExists(followerId);
    return subscriptionRepository.findFolloweesAmountByFollowerId(followerId);
  }
}
