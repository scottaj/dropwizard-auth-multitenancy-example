package middleware.security;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class CustomSecurityContext implements SecurityContext {
  private final CustomAuthUser principal;
  private final Long tenantId;
  private final SecurityContext securityContext;

  public CustomSecurityContext(CustomAuthUser principal, Long tenantId, SecurityContext securityContext) {
    this.principal = principal;
    this.tenantId = tenantId;
    this.securityContext = securityContext;
  }

  @Override
  public Principal getUserPrincipal() {
    return principal;
  }

  @Override
  public boolean isUserInRole(String role) {
    return principal.getTenantId().equals(tenantId) && role.equals(principal.getRole().name());
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
