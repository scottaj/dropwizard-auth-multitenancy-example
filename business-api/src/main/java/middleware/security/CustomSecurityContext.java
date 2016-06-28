package middleware.security;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class CustomSecurityContext implements SecurityContext {
  private final CustomAuthUser principal;
  private final SecurityContext securityContext;

  public CustomSecurityContext(CustomAuthUser principal, SecurityContext securityContext) {
    this.principal = principal;
    this.securityContext = securityContext;
  }

  @Override
  public Principal getUserPrincipal() {
    return principal;
  }

  @Override
  public boolean isUserInRole(String role) {
    return role.equals(principal.getRole().name());
  }

  @Override
  public boolean isSecure() {
    return securityContext.isSecure();
  }

  @Override
  public String getAuthenticationScheme() {
    return "CUSTOM_TOKEN";
  }
}
