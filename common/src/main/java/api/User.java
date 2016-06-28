package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dao.Role;

public class User {
  private Long id;
  private String name;
  private Role role;

  @JsonCreator
  public User(
    @JsonProperty("id") Long id,
    @JsonProperty("name") String name,
    @JsonProperty("role") Role role
  ) {
    this.id = id;
    this.name = name;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
