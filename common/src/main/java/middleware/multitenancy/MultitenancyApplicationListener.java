package middleware.multitenancy;

import dao.TenantDAO;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

public class MultitenancyApplicationListener implements ApplicationEventListener {
  private TenantDAO tenantDAO;

  public MultitenancyApplicationListener(TenantDAO tenantDAO) {
    this.tenantDAO = tenantDAO;
  }

  @Override
  public void onEvent(ApplicationEvent event) {}

  @Override
  public RequestEventListener onRequest(RequestEvent requestEvent) {
    return new MultitenancyRequestListener(tenantDAO);
  }
}
