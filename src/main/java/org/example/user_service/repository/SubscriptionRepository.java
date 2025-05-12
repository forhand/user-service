package org.example.user_service.repository;

import org.example.user_service.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface SubscriptionRepository extends CrudRepository<User, Long> {

  @Query(nativeQuery = true, value = "insert into subscriptions (follower_id, followee_id) values (:followerId, :followeeId)")
  @Modifying
  @Transactional
  void followUser(long followerId, long followeeId);

  @Query(nativeQuery = true, value = "select exists(select 1 from subscriptions where follower_id = :followerId and followee_id = :followeeId)")
  boolean existsByFollowerIdAndFolloweeId(long followerId, long followeeId);

  @Query(nativeQuery = true, value = "delete from subscriptions where follower_id = :followerId and followee_id = :followeeId")
  @Modifying
  void unfollowUser(long followerId, long followeeId);

  @Query(nativeQuery = true, value = "select count(id) from subscriptions where follower_id = :followerId")
  int findFolloweesAmountByFollowerId(long followerId);

  @Query(nativeQuery = true, value = """
          select followee_id from subscriptions where follower_id = :followerId
          """)
  List<Long> findFolloweeIdsByFollowerId(long followerId);


  @Query(nativeQuery = true, value = """
          select follower_id from subscriptions where followee_id = :followeeId
          """)
  List<Long> findFollowerIdsByFolloweeId(long followeeId);

  @Query(nativeQuery = true, value = "select count(id) from subscriptions where followee_id = :followeeId")
  int findFollowersAmountByFolloweeId(long followeeId);



  @Query(nativeQuery = true, value = """
          select u.* from users as u
          join subscriptions as subs on u.id = subs.followee_id
          where subs.follower_id = :followerId
          """)
  Stream<User> findByFollowerId(long followerId);

  @Query(nativeQuery = true, value = """
          select u.* from users as u
          join subscriptions as subs on u.id = subs.follower_id
          where subs.followee_id = :followeeId
          """)
  Stream<User> findByFolloweeId(long followeeId);
}