package org.example.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.user_service.config.context.UserContext;
import org.example.user_service.dto.event.user.UserRetrievedEvent;
import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.dto.userDto.UserFilterDto;
import org.example.user_service.dto.userDto.UserRegistrationDto;
import org.example.user_service.entity.User;
import org.example.user_service.filter.user.UserFilter;
import org.example.user_service.handler.exception.ResourceNotFoundException;
import org.example.user_service.mapper.UserMapper;
import org.example.user_service.repository.UserRepository;
import org.example.user_service.service.encoder.PasswordEncoder;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final MessageSource messageSource;
  private final PasswordEncoder encoder;
  private final List<UserFilter> userFilters;
  private final EventPublisherService eventPublisherService;
  private final UserContext userContext;

  public UserDto getUser(long userId, boolean skipEvent) {
    User user = getUserById(userId);
    log.info("User {} found", user);
    if(!skipEvent) {
      UserRetrievedEvent event = userMapper.toUserRetrievedEvent(user);
      event.setActorId(userContext.getUserId());
      eventPublisherService.publishEvent(event);
    }
    return userMapper.toDto(user);
  }

  public UserDto createUser(UserRegistrationDto userDto) {
    User user = userMapper.toEntity(userDto);
    user.setPassword(encoder.encode(userDto.getPassword()));
    User savedUser = userRepository.save(user);
    log.info("User {} created", savedUser.getId());

    return userMapper.toDto(savedUser);
  }

  public List<UserDto> getUsersByIds(List<Long> ids) {
    return userRepository.findAllById(ids).stream()
            .map(userMapper::toDto).toList();
  }

  public List<UserDto> getUsersByIds(List<Long> ids, UserFilterDto filterDto) {
    Stream<User> filteredUsers = userRepository.findAllById(ids).stream();

    for (UserFilter filter : userFilters) {
      if(filter.isApplicable(filterDto)) {
        filteredUsers = filter.apply(filteredUsers, filterDto);
      }
    }

    return userMapper.toDtos(filteredUsers.toList());
  }

  public UserDto deactivateUserProfile(Long userId) {
    User user = getUserById(userId);
    if (user.isActive()) {
      user.setActive(false);
      log.info("User {} deactivated", userId);
      // todo: опубликовать событие об изменении статуса аккаунта
      // todo: уведомить пользователя о деактивации аккаунта через почту или смс
      // todo: сохранить в БД на удаление через период времени (30 дней)
      user = userRepository.save(user);
    }
    return userMapper.toDto(user);
  }

  public void validateUserExists(long userId) {
    if (!userRepository.existsById(userId)) {
      throw new ResourceNotFoundException(
              messageSource.getMessage("exception.user.not_found", new Object[]{userId}, Locale.getDefault())
      );
    }
  }
  
  private User getUserById(long userId) {
    return userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException(messageSource.getMessage("exception.user.not_found", new Object[]{userId}, Locale.getDefault())
            ));
  }
}
