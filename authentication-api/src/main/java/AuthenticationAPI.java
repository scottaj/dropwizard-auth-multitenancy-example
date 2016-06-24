import dao.TokenDAO;
import dao.UserDAO;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;
import resources.AuthResource;

public class AuthenticationAPI extends Application<ExampleConfig> {
  public static void main(String args[]) throws Exception {
    new AuthenticationAPI().run(args);
  }

  private final ScanningHibernateBundle<ExampleConfig> hibernate = new ScanningHibernateBundle<ExampleConfig>("dao.entities") {
    @Override
    public PooledDataSourceFactory getDataSourceFactory(ExampleConfig config) {
      return config.getDatabaseConfig();
    }
  };

  @Override
  public void initialize(Bootstrap<ExampleConfig> bootstrap) {
    bootstrap.addBundle(hibernate);
  }

  @Override
  public void run(ExampleConfig config, Environment environment) throws Exception {
    SessionFactory sessionFactory = hibernate.getSessionFactory();
    UserDAO userDAO = new UserDAO(sessionFactory);
    TokenDAO tokenDAO = new TokenDAO(sessionFactory, userDAO);
    AuthResource authResource = new AuthResource(userDAO, tokenDAO);

    environment.jersey().register(authResource);
  }
}
