package org.example.user_service.mapper;

import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.dto.userDto.UserRegistrationDto;
import org.example.user_service.entity.User;
import org.example.user_service.util.container.UserDataContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
  private UserMapperImpl userMapper = new UserMapperImpl();
  private UserDataContainer container;
  User user;
  UserDto userDto;

  @BeforeEach
  void setUp() {
    container = new UserDataContainer();
    user = container.getUser();
    userDto = container.getUserDto();
  }


  @Test
  void toDto() {
    UserDto actDto = userMapper.toDto(user);

    assertEquals(userDto, actDto);
  }

  @Test
  void toDto_WOFollowers() {
    user.setFollowers(null);
    userDto.setFollowerIds(new ArrayList<>());

    UserDto actDto = userMapper.toDto(user);

    assertEquals(userDto, actDto);
  }

  @Test
  void toDto_WOFollowees() {
    user.setFollowees(null);
    userDto.setFolloweeIds(new ArrayList<>());

    UserDto actDto = userMapper.toDto(user);

    assertEquals(userDto, actDto);
  }

  @Test
  void toEntity() {
   User expEntity = User.builder()
           .username(userDto.getUsername())
           .email(userDto.getEmail())
           .active(userDto.isActive())
           .age(userDto.getAge())
           .build();
    User actEntity = userMapper.toEntity(userDto);

    assertEquals(expEntity, actEntity);
  }

  @Test
  void toEntity_WOUserId() {
    userDto.setId(null);
    user.setId(null);

    User entity = userMapper.toEntity(userDto);

    assertEquals(user.getId(), entity.getId());
  }

  @Test
  void testToEntity() {
    UserRegistrationDto dto = container.getUserRegistrationDto();
    User expEntity = User.builder()
            .username(dto.getUsername())
            .email(dto.getEmail())
            .age(dto.getAge())
            .build();

    User actEntity = userMapper.toEntity(dto);

    assertEquals(expEntity, actEntity);
  }
}