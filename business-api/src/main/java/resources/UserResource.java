package resources;

import api.User;
import dao.UserDAO;
import dao.entities.UserModel;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
  private UserDAO userDAO;

  public UserResource(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @POST
  @UnitOfWork
  @RolesAllowed({"MANAGER"})
  public User createUser(User userToCreate) {
    UserModel createdUser = userDAO.createUser(userToCreate);
    return new User(createdUser.getId(), createdUser.getName());
  }
}
