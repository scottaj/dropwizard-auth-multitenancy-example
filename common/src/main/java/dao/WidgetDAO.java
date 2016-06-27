package dao;

import api.Widget;
import dao.entities.WidgetModel;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class WidgetDAO extends AbstractDAO<WidgetModel> {
  public WidgetDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<WidgetModel> getWidgets(WidgetScope scope) {
    Criteria filter = criteria()
      .add(Restrictions.eq("scope", scope));

    return list(filter);
  }

  public WidgetModel createWidget(Widget widget) {
    WidgetModel widgetModel = new WidgetModel();
    widgetModel.setName(widget.getName());
    widgetModel.setScope(widget.getScope());

    return persist(widgetModel);
  }
}
