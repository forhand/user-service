package org.example.user_service.util.container;

import org.example.user_service.dto.subcription.FollowRequest;

public class SubscriptionDataContainer {
  private Long id;
  private Long followerId;
  private Long followeeId;

  public SubscriptionDataContainer() {
    this.id = 0L;
    this.followerId = ++id;
    this.followeeId = ++id;
  }

  public FollowRequest getFollowRequest() {
    return FollowRequest.builder()
            .followerId(followerId)
            .followeeId(followeeId)
            .build();
  }

}
