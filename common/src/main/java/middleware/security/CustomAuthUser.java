package middleware.security;

import dao.Role;

import java.security.Principal;

public class CustomAuthUser implements Principal {
  private final String name;
  private final Role role;
  private final Long tenantId;

  public CustomAuthUser(String name, Long tenantId, Role role) {
    this.name = name;
    this.tenantId = tenantId;
    this.role = role;
  }

  @Override
  public String getName() {
    return name;
  }

  public Role getRole() {
    return role;
  }

  public Long getTenantId() {
    return tenantId;
  }
}
