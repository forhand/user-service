package org.example.user_service.exceptionHendler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
  private String message;
  private String url;
}
