package dao;

import dao.entities.TenantModel;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class TenantDAO extends AbstractDAO<TenantModel> {
  public TenantDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Optional<TenantModel> getTenant(Long id) {
    return Optional.ofNullable(get(id));
  }
}
