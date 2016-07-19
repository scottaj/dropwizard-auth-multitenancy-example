package dao;

import api.User;
import com.google.common.base.Optional;
import dao.entities.UserModel;
import io.dropwizard.hibernate.AbstractDAO;
import middleware.multitenancy.TenantRequestData;
import org.hibernate.SessionFactory;

public class UserDAO extends AbstractDAO<UserModel> {
  public UserDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Optional<UserModel> getUser(Long userId) {
    return Optional.fromNullable(get(userId));
  }

  public UserModel createUser(User userToCreate) {
    UserModel user = new UserModel();
    user.setName(userToCreate.getName());
    user.setRole(userToCreate.getRole());
    user.setTenant(TenantRequestData.tenant.get());

    return persist(user);
  }
}
