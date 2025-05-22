package org.example.user_service.service.encoder;

import org.springframework.stereotype.Component;

// Заглушка
@Component
public class PasswordEncoder {
  public String encode(String password) {
    return password + "_encoded";
  }
}
