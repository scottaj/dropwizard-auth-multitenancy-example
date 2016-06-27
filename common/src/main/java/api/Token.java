package api;

import java.util.UUID;

public class Token {
  private Long userId;
  private UUID token;

  public Token(Long userId, UUID token) {
    this.userId = userId;
    this.token = token;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public UUID getToken() {
    return token;
  }

  public void setToken(UUID token) {
    this.token = token;
  }
}
