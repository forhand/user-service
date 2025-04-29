package org.example.user_service.mapper;

import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    private UserMapperImpl userMapper = new UserMapperImpl();
    User user;
    UserDto userDto;

    @BeforeEach
    void setUp() {
        Long userId = 1L;
        String username = "username";
        String password = "password";
        boolean active = true;

        user = User.builder()
                .id(userId)
                .username(username)
                .password(password)
                .active(active)
                .build();

        userDto = UserDto.builder()
                .id(userId)
                .username(username)
                .password(password)
                .active(active)
                .build();
    }


    @Test
    void toDto() {
        UserDto dto = userMapper.toDto(user);

        assertEquals(userDto, dto);
    }

    @Test
    void toEntity() {
        User entity = userMapper.toEntity(userDto);

        assertEquals(user, entity);
    }

    @Test
    void toEntityWOUserId() {
        userDto.setId(null);
        user.setId(null);

        User entity = userMapper.toEntity(userDto);

        assertEquals(user, entity);
    }
}