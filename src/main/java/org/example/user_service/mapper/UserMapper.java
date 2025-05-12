package org.example.user_service.mapper;

import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.dto.userDto.UserRegistrationDto;
import org.example.user_service.entity.User;
import org.example.user_service.entity.contact.PreferredContact;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {

  @Mapping(target = "followerIds", expression = "java(mapToUserIds(entity.getFollowers()))")
  @Mapping(target = "followeeIds", expression = "java(mapToUserIds(entity.getFollowees()))")
  @Mapping(target = "preferredContact", expression = "java(mapToPreferredContact(entity))")
  UserDto toDto(User entity);

  @Mapping(target = "id", ignore = true)
  User toEntity(UserDto dto);

  @Mapping(target = "password", ignore = true)
  User toEntity(UserRegistrationDto dto);

  List<UserDto> toDtos(List<User> list);

  default PreferredContact mapToPreferredContact(User user) {
    if (user.getContactPreference() == null) {
      return null;
    }
    return user.getContactPreference().getPreference();
  }

  default List<Long> mapToUserIds(List<User> users) {
    if (users == null) {
      return Collections.emptyList();
    }
    return users.stream().map(User::getId).toList();
  }

}
