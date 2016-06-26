import dao.UserDAO;
import dao.WidgetDAO;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;
import resources.UserResource;
import resources.WidgetResource;

public class BusinessAPI extends Application<ExampleConfig> {
  public static void main(String[] args) throws Exception {
    new BusinessAPI().run(args);
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
    WidgetDAO widgetDAO = new WidgetDAO(sessionFactory);
    UserDAO userDAO = new UserDAO(sessionFactory);

    environment.jersey().register(new WidgetResource(widgetDAO));
    environment.jersey().register(new UserResource(userDAO));
  }
}
