import dao.TokenDAO;
import dao.UserDAO;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import resources.AuthResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class AuthenticationAPI extends Application<ExampleConfig> {
  public static void main(String args[]) throws Exception {
    new AuthenticationAPI().run(args);
  }

  private final ScanningHibernateBundle<ExampleConfig> hibernate = new ScanningHibernateBundle<ExampleConfig>("dao.entities") {
    @Override
    protected void configure(Configuration configuration) {
      // Register package so global filters in package-info.java get seen.
      configuration.addPackage("dao.entities");
      super.configure(configuration);
    }

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
    enableCORS(environment);

    SessionFactory sessionFactory = hibernate.getSessionFactory();
    UserDAO userDAO = new UserDAO(sessionFactory);
    TokenDAO tokenDAO = new TokenDAO(sessionFactory, userDAO);
    AuthResource authResource = new AuthResource(userDAO, tokenDAO);

    environment.jersey().register(authResource);
  }

  private void enableCORS(Environment environment) {
    FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
    filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
    filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
  }
}
