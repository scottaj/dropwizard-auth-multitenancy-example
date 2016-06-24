package resources;

import api.Token;
import api.User;
import dao.TokenDAO;
import dao.UserDAO;
import dao.entities.TokenModel;
import dao.entities.UserModel;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
  private UserDAO userDAO;
  private TokenDAO tokenDAO;

  public AuthResource(UserDAO userDAO, TokenDAO tokenDAO) {
    this.userDAO = userDAO;
    this.tokenDAO = tokenDAO;
  }

  @GET
  @Path("/token/{userId}")
  @UnitOfWork
  public Token getToken(@PathParam("userId") Long userId) {
    TokenModel token = tokenDAO.findOrCreateTokenForUser(userId);

    if (token != null) {
      return new Token(token.getUser().getId(), token.getId());
    }

    throw new WebApplicationException(Status.UNAUTHORIZED);
  }

  @POST
  @Path("/user")
  @UnitOfWork
  public User createUser(User userToCreate) {
    UserModel createdUser = userDAO.createUser(userToCreate);
    return new User(createdUser.getId(), createdUser.getName());
  }
}
