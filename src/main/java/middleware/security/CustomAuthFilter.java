package middleware.security;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.UUID;

public class CustomAuthFilter extends AuthFilter<CustomCredentials, CustomAuthUser> {
  private CustomAuthenticator authenticator;

  public CustomAuthFilter(CustomAuthenticator authenticator) {

    this.authenticator = authenticator;
  }

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    Optional<CustomAuthUser> authenticatedUser;

    try {
      CustomCredentials credentials = getCredentials(requestContext);
      authenticatedUser = authenticator.authenticate(credentials);
    } catch (AuthenticationException e) {
      throw new WebApplicationException("Unable to validate credentials", Response.Status.UNAUTHORIZED);
    }

    if (!authenticatedUser.isPresent()) {
      throw new WebApplicationException("Credentials not valid", Response.Status.UNAUTHORIZED);
    }
  }

  private CustomCredentials getCredentials(ContainerRequestContext requestContext) {
    CustomCredentials credentials = new CustomCredentials();

    try {
      String rawToken = requestContext
        .getCookies()
        .get("auth_token")
        .getValue();

      String rawUserId = requestContext
        .getCookies()
        .get("auth_user")
        .getValue();

      credentials.setToken(UUID.fromString(rawToken));
      credentials.setUserId(Long.valueOf(rawUserId));
    } catch (Exception e) {
      throw new WebApplicationException("Unable to parse credentials", Response.Status.UNAUTHORIZED);
    }


    return credentials;
  }
}
