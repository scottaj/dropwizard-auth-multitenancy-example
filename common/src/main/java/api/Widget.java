package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dao.WidgetScope;

public class Widget {
  private Integer id;
  private String name;
  private WidgetScope scope;

  @JsonCreator
  public Widget(
    @JsonProperty("id") Integer id,
    @JsonProperty("name") String name,
    @JsonProperty("scope") WidgetScope scope
  ) {
    this.id = id;
    this.name = name;
    this.scope = scope;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public WidgetScope getScope() {
    return scope;
  }

  public void setScope(WidgetScope scope) {
    this.scope = scope;
  }
}
