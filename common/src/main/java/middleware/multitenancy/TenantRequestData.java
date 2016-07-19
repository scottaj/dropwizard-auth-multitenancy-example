package middleware.multitenancy;

import dao.entities.TenantModel;

public class TenantRequestData {
  public static final ThreadLocal<TenantModel> tenant = new ThreadLocal<>();
}
