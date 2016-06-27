package resources;

import api.Widget;
import dao.WidgetDAO;
import dao.WidgetScope;
import dao.entities.WidgetModel;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/widgets")
@Produces(MediaType.APPLICATION_JSON)
public class WidgetResource {
  private WidgetDAO widgetDAO;

  public WidgetResource(WidgetDAO widgetDAO) {
    this.widgetDAO = widgetDAO;
  }

  @POST
  @UnitOfWork
  @RolesAllowed({"MANAGER"})
  public Widget createWidget(Widget widget) {
    WidgetModel widgetModel = widgetDAO.createWidget(widget);

    return new Widget(widgetModel.getId(), widgetModel.getName(), widgetModel.getScope());
  }

  @GET
  @Path("/public")
  @UnitOfWork
  @PermitAll
  public List<Widget> getPublicWidgets() {
    return getWidgetsForScope(WidgetScope.PUBLIC);
  }

  @GET
  @Path("/private")
  @UnitOfWork
  @RolesAllowed({"EMPLOYEE", "MANAGER"})
  public List<Widget> getPrivateWidgets() {
    return getWidgetsForScope(WidgetScope.PRIVATE);
  }

  @GET
  @Path("/top-secret")
  @UnitOfWork
  @RolesAllowed({"MANAGER"})
  public List<Widget> getTopSecretWidgets() {
    return getWidgetsForScope(WidgetScope.TOP_SECRET);
  }

  private List<Widget> getWidgetsForScope(WidgetScope scope) {
    return widgetDAO.getWidgets(scope)
      .stream()
      .map(widgetModel -> new Widget(widgetModel.getId(), widgetModel.getName(), widgetModel.getScope()))
      .collect(Collectors.toList());
  }
}
