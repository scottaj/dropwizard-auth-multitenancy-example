package dao;

import com.google.common.base.Optional;
import dao.entities.TokenModel;
import dao.entities.UserModel;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.UUID;

public class TokenDAO extends AbstractDAO<TokenModel> {
  private UserDAO userDAO;

  public TokenDAO(SessionFactory sessionFactory, UserDAO userDAO) {
    super(sessionFactory);
    this.userDAO = userDAO;
  }

  public TokenModel findOrCreateTokenForUser(Long userId) {
    Optional<UserModel> foundUser = userDAO.getUser(userId);
    Optional<TokenModel> token = Optional.absent();

    if (foundUser.isPresent()) {
      UserModel user = foundUser.get();
      token = findTokenForUser(user);

      if (!token.isPresent()) {
        TokenModel model = new TokenModel();
        model.setUser(user);
        model.setId(UUID.randomUUID());
        return persist(model);
      }
    }

    return token.orNull();
  }

  public Optional<TokenModel> findTokenForUser(UserModel user) {
    Criteria criteria = criteria()
      .createAlias("user", "u")
      .add(Restrictions.eq("u.id", user.getId()));

    return Optional.fromNullable(uniqueResult(criteria));
  }
}
