package org.example.user_service.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
  private String message;
  private String url;
  private List<String> errorMessages;
}
