package org.example.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.user_service.dto.subcription.FollowRequest;
import org.example.user_service.handler.GlobalExceptionHandler;
import org.example.user_service.service.subscription.SubscriptionService;
import org.example.user_service.util.container.SubscriptionDataContainer;
import org.example.user_service.util.container.UserDataContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SubscriptionControllerTest {
  @Mock
  private SubscriptionService service;
  @InjectMocks
  private SubscriptionController controller;
  private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private UserDataContainer container;
  private final String uri = "/api/subscription";
  private SubscriptionDataContainer subscriptionDataContainer;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();

    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    container = new UserDataContainer();
    subscriptionDataContainer = new SubscriptionDataContainer();
  }

  @Test
  void followUser_success() throws Exception {
    String url = "%s/follow".formatted(this.uri);
    FollowRequest request = subscriptionDataContainer.getFollowRequest();

    mockMvc.perform(post(url)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @Test
  void followUser_invalidFollowerId() throws Exception {
    String url = "%s/follow".formatted(this.uri);
    FollowRequest request = FollowRequest.builder()
            .followerId(-1L)
            .build();

    mockMvc.perform(post(url)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

  @Test
  void followUser_invalidFolloweeId() throws Exception {
    String url = "%s/follow".formatted(this.uri);
    FollowRequest request = FollowRequest.builder()
            .followeeId(0L)
            .build();

    mockMvc.perform(post(url)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }


}