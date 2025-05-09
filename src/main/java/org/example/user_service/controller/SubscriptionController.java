package org.example.user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.user_service.dto.subcription.FollowRequest;
import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.dto.userDto.UserFilterDto;
import org.example.user_service.service.subscription.SubscriptionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  @PostMapping("/follow")
  public void followUser(@RequestBody @Valid FollowRequest followRequest) {
    long followerId = followRequest.getFollowerId();
    long followeeId = followRequest.getFolloweeId();
    subscriptionService.followUser(followerId, followeeId);
  }

  @DeleteMapping("/unfollow")
  public void unfollowUser(@RequestBody @Valid FollowRequest followRequest) {
    long followerId = followRequest.getFollowerId();
    long followeeId = followRequest.getFolloweeId();
    subscriptionService.unfollowUser(followerId, followeeId);
  }

  @GetMapping("/followerCount/{userId}")
  public int getFollowerCount(@PathVariable("userId") long followerId) {
      return subscriptionService.getFollowerCount(followerId);
  }

  @GetMapping("/following/{userId}")
  public List<UserDto> getFollowing(@PathVariable("userId")long followeeId, UserFilterDto filterDto){
    return subscriptionService.getFollowing(followeeId, filterDto);
  }

}
