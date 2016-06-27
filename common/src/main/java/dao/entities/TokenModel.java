package dao.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "UserToken")
public class TokenModel {
  @Id
  private UUID id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "userId")
  private UserModel user;

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public void setUser(UserModel user) {
    this.user = user;
  }

  public UserModel getUser() {
    return user;
  }
}
