@FilterDef(
  name = "restrictToTenant",
  defaultCondition = "tenantId = :tenantId",
  parameters = @ParamDef(name = "tenantId", type = "long")
)
package dao.entities;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
