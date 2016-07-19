package middleware.multitenancy;

import dao.TenantDAO;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.hibernate.SessionFactory;

import javax.annotation.Priority;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(10000) // This needs to be the last listener to run
public class MultitenancyApplicationListener implements ApplicationEventListener {
  private TenantDAO tenantDAO;
  private SessionFactory sessionFactory;

  public MultitenancyApplicationListener(TenantDAO tenantDAO, SessionFactory sessionFactory) {
    this.tenantDAO = tenantDAO;
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void onEvent(ApplicationEvent event) {}

  @Override
  public RequestEventListener onRequest(RequestEvent requestEvent) {
    return new MultitenancyRequestListener(tenantDAO, sessionFactory);
  }
}
