package middleware.security;

import dao.Role;

import java.security.Principal;

public class CustomAuthUser implements Principal {
  private final String name;
  private final Role role;

  public CustomAuthUser(String name, Role role) {
    this.name = name;
    this.role = role;
  }

  @Override
  public String getName() {
    return name;
  }

  public Role getRole() {
    return role;
  }
}
