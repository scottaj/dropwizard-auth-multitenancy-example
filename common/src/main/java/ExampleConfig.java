import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.constraints.NotNull;

public class ExampleConfig extends Configuration {
  @NotNull
  @JsonProperty("database")
  private DataSourceFactory databaseConfig;

  public DataSourceFactory getDatabaseConfig() {
    return databaseConfig;
  }
}
