import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MigrationApp extends Application<ExampleConfig> {
  public static void main(String[] args) throws Exception {
    new MigrationApp().run(args);
  }

  @Override
  public void initialize(Bootstrap<ExampleConfig> bootstrap) {
    bootstrap.addBundle(new MigrationsBundle<ExampleConfig>() {
      @Override
      public DataSourceFactory getDataSourceFactory(ExampleConfig configuration) {
        return configuration.getDatabaseConfig();
      }
    });
  }

  @Override
  public void run(ExampleConfig exampleConfig, Environment environment) throws Exception {

  }
}
