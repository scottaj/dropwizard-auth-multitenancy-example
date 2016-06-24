package dao;

import dao.entities.TokenModel;
import dao.entities.UserModel;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Optional;
import java.util.UUID;

public class TokenDAO extends AbstractDAO<TokenModel> {
  private UserDAO userDAO;

  public TokenDAO(SessionFactory sessionFactory, UserDAO userDAO) {
    super(sessionFactory);
    this.userDAO = userDAO;
  }

  public TokenModel findOrCreateTokenForUser(Long userId) {
    Optional<UserModel> foundUser = userDAO.getUser(userId);
    TokenModel token = null;

    if (foundUser.isPresent()) {
      UserModel user = foundUser.get();
      token = findTokenForUser(user);

      if (token == null) {
        token = new TokenModel();
        token.setUser(user);
        token.setId(UUID.randomUUID());
        return persist(token);
      }
    }

    return token;
  }

  private TokenModel findTokenForUser(UserModel user) {
    Criteria criteria = criteria()
      .createAlias("user", "u")
      .add(Restrictions.eq("u.id", user.getId()));

    return uniqueResult(criteria);
  }
}
