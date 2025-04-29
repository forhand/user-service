package org.example.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.user_service.dto.userDto.UserDto;
import org.example.user_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
  @Mock
  private UserService userService;
  @InjectMocks
  private UserController controller;
  private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private String url = "/api/users";

  @BeforeEach
  void setUp() {

    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  @Test
  void testGetUser() throws Exception {
    long userId = 1L;
    String urlTemplate = url + "/" + userId;
    UserDto expectedUser = UserDto.builder().id(userId).build();
    when(userService.getUser(userId)).thenReturn(expectedUser);

    mockMvc.perform(get(urlTemplate))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(expectedUser.getId().intValue())));
  }

  @Test
  void testGetUserWithInvalidUserId() throws Exception {
    long invalidUserId = 0L;
    String urlTemplate = url + "/" + invalidUserId;

    mockMvc.perform(get(urlTemplate))
            .andExpect(status().isBadRequest());
  }

  @Test
  void testCreateUser() throws Exception {
    String urlTemplate = url + "/register";
    UserDto requestDto = UserDto.builder().username("user").build();
    UserDto savedUser = UserDto.builder().id(1L).username("user").build();
    when(userService.createUser(requestDto)).thenReturn(savedUser);

    mockMvc.perform(post(urlTemplate)
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(savedUser.getId().intValue())))
            .andExpect(jsonPath("$.username", is(savedUser.getUsername())));
  }

  @Test
  void testCreateUserWithNull() throws Exception {
    String urlTemplate = url + "/register";
    UserDto requestDto = null;

    mockMvc.perform(post(urlTemplate)
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

  @Test
  void testGetUsersByIds() throws Exception {
    String urlTemplate = url + "/list";
    List<Long> userIds = Arrays.asList(1L, 2L, 3L);
    List<UserDto> users = createUsers(userIds);
    when(userService.getUsersByIds(userIds)).thenReturn(users);


    mockMvc.perform(get(urlTemplate)
              .param("userId", String.valueOf(userIds.get(0)))
              .param("userId", String.valueOf(userIds.get(1)))
              .param("userId", String.valueOf(userIds.get(2))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(users.size())))
            .andExpect(jsonPath("$[0].id", is(users.get(0).getId().intValue())));
  }

  private static List<UserDto> createUsers(List<Long> userIds) {
    List<UserDto> users = new ArrayList<>();
    for (Long userId : userIds) {
      users.add(UserDto.builder().id(userId).build());
    }
    return users;
  }

}