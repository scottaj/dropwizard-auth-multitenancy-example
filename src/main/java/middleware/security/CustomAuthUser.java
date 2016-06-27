package middleware.security;

import java.security.Principal;

public class CustomAuthUser implements Principal {
  private String name;

  public CustomAuthUser(Long id, String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }
}
