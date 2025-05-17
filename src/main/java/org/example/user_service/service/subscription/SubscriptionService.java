package org.example.user_service.service.subscription;

import lombok.RequiredArgsConstructor;
import org.example.user_service.dto.event.subscription.SubscribedEvent;
import org.example.user_service.dto.event.subscription.UnsubscribedEvent;
import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.dto.userDto.UserFilterDto;
import org.example.user_service.repository.SubscriptionRepository;
import org.example.user_service.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;
  private final UserService userService;
  private final MessageSource messageSource;
  private final SubscriptionDataValidator validator;
  private final SubscriptionEventPublisherService publisherService;

  public void followUser(long followerId, long followeeId) {
    validator.validateFollowUser(followerId, followeeId);
    subscriptionRepository.followUser(followerId, followeeId);
    publisherService.publishUserSubscriptionEvent(new SubscribedEvent(followerId, followeeId, LocalDateTime.now()));
  }

  @Transactional
  public void unfollowUser(long followerId, long followeeId) {
    validator.validateUnfollowUser(followerId, followeeId);
    subscriptionRepository.unfollowUser(followerId, followeeId);
    publisherService.publishUserUnsubscriptionEvent(new UnsubscribedEvent(followerId, followeeId, LocalDateTime.now()));
  }

  public int getFollowerCount(long followeeId) {
    validator.validateUserExists(followeeId);
    return subscriptionRepository.findFollowersAmountByFolloweeId(followeeId);
  }

  public List<UserDto> getFollowing(long followeeId, UserFilterDto filterDto) {
    List<Long> followees = subscriptionRepository.findFolloweeIdsByFollowerId(followeeId);

    return userService.getUsersByIds(followees, filterDto);
  }

  public List<UserDto> getFollowers(long followeeId, UserFilterDto filterDto) {
    List<Long> followers = subscriptionRepository.findFollowerIdsByFolloweeId(followeeId);

    return userService.getUsersByIds(followers, filterDto);
  }

  public int getFollowingCount(long followerId) {
    return subscriptionRepository.findFolloweesAmountByFollowerId(followerId);
  }
}
