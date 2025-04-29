package org.example.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.entity.User;
import org.example.user_service.exceptionHendler.exception.UserNotFoundException;
import org.example.user_service.mapper.UserMapper;
import org.example.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserDto getUser(long userId) {
    User user = getUserById(userId);
    return userMapper.toDto(user);
  }

  public UserDto createUser(UserDto userDto) {
    User user = userMapper.toEntity(userDto);
    User savedUser = userRepository.save(user);
    log.info("User {} created", savedUser.getId());

    return userMapper.toDto(savedUser);
  }

  public List<UserDto> getUsersByIds(List<Long> ids) {
    return userRepository.findAllById(ids).stream()
            .map(userMapper::toDto).toList();
  }

  public UserDto deactivateUserProfile(Long userId) {
    User user = getUserById(userId);
    if(user.isActive()){
      user.setActive(false);
      log.info("User {} deactivated", userId);
      // todo: опубликовать событие об изменении статуса аккаунта
      // todo: уведомить пользователя о деактивации аккаунта через почту или смс
      // todo: сохранить в БД на удаление через период времени (30 дней)
      user = userRepository.save(user);
    }
    return userMapper.toDto(user);
  }

  private User getUserById(long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }
}
