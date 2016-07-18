package dao.entities;

import dao.WidgetScope;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Widget")
public class WidgetModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @Enumerated(EnumType.STRING)
  private WidgetScope scope;

  @ManyToOne
  @JoinColumn(name = "tenantId")
  private TenantModel tenant;

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

  public TenantModel getTenant() {
    return tenant;
  }

  public void setTenant(TenantModel tenant) {
    this.tenant = tenant;
  }
}
