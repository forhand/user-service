package org.example.user_service.mapper;

import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.dto.userDto.UserRegistrationDto;
import org.example.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {

  @Mapping(target = "followerIds", expression = "java(mapToUserIds(entity.getFollowers()))")
  @Mapping(target = "followeeIds", expression = "java(mapToUserIds(entity.getFollowees()))")
  UserDto toDto(User entity);

  @Mapping(target = "id", ignore = true)
  User toEntity(UserDto dto);

  @Mapping(target = "password", ignore = true)
  User toEntity(UserRegistrationDto dto);

  default List<Long> mapToUserIds(List<User> users) {
    return null;
  }

  List<UserDto> toDtos(List<User> list);
}
