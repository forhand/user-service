package org.example.user_service.filter.user;

import org.example.user_service.dto.userDto.UserFilterDto;
import org.example.user_service.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class UserMinAgeFilter implements UserFilter{
  @Override
  public boolean isApplicable(UserFilterDto filterDto) {
    return filterDto.getMinAge() != null;
  }

  @Override
  public Stream<User> apply(Stream<User> users, UserFilterDto filterDto) {
    return users.filter(user -> user.getAge() >= filterDto.getMinAge());
  }
}
