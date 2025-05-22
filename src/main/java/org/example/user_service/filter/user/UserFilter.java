package org.example.user_service.filter.user;

import org.example.user_service.dto.userDto.UserFilterDto;
import org.example.user_service.entity.User;

import java.util.stream.Stream;

public interface UserFilter {
  boolean isApplicable(UserFilterDto filterDto);

  Stream<User> apply(Stream<User> users, UserFilterDto filterDto);
}
