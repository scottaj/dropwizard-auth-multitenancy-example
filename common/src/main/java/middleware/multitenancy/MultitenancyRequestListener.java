package middleware.multitenancy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dao.TenantDAO;
import dao.entities.TenantModel;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.hibernate.SessionFactory;

import javax.ws.rs.WebApplicationException;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;

public class MultitenancyRequestListener implements RequestEventListener {
  private TenantDAO tenantDAO;
  private SessionFactory sessionFactory;
  private Cache<Long, TenantModel> tenants;

  public MultitenancyRequestListener(TenantDAO tenantDAO, SessionFactory sessionFactory) {
    this.tenantDAO = tenantDAO;
    this.sessionFactory = sessionFactory;

    tenants = CacheBuilder.newBuilder()
      .maximumSize(1000)
      .expireAfterWrite(1, TimeUnit.HOURS)
      .build();
  }

  @Override
  public void onEvent(RequestEvent event) {
    if (event.getType() == RequestEvent.Type.RESOURCE_METHOD_START) {
      try {
        Long tenantId = Long.valueOf(event.getContainerRequest().getUriInfo().getPathParameters().getFirst("tenantId"));
        TenantModel tenant = tenants.get(tenantId, () -> tenantDAO.getTenant(tenantId).get());

        TenantRequestData.tenant.set(tenant);
        sessionFactory.getCurrentSession().enableFilter("restrictToTenant").setParameter("tenantId", tenantId);
      } catch (Exception e) {
        throw new WebApplicationException("Tenant not found", FORBIDDEN);
      }
    }
  }
}
