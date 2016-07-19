package middleware.security;

import java.util.UUID;

public class CustomCredentials {
  private UUID token;
  private Long userId;

 public UUID getToken() {
    return token;
  }

  public void setToken(UUID token) {
    this.token = token;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getUserId() {
    return userId;
  }
}
