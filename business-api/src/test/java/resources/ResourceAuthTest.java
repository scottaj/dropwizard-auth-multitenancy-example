package resources;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceAuthTest {
  @Test
  public void resourceMethodsHaveRoles() {
    ConfigurationBuilder configuration = new ConfigurationBuilder()
      .setUrls(ClasspathHelper.forPackage("resources"))
      .setScanners(new MethodAnnotationsScanner())
      .filterInputsBy(new FilterBuilder().includePackage("resources"));

    Reflections reflections = new Reflections(configuration);

    ImmutableSet<Method> resourceMethods = ImmutableSet.<Method>builder()
      .addAll(reflections.getMethodsAnnotatedWith(GET.class))
      .addAll(reflections.getMethodsAnnotatedWith(POST.class))
      .addAll(reflections.getMethodsAnnotatedWith(PUT.class))
      .addAll(reflections.getMethodsAnnotatedWith(DELETE.class))
      .build();

    assertThat(resourceMethods.size()).as("No resource methods found, are you sure you entered the right package?").isGreaterThan(0);

    for (Method method : resourceMethods) {
      String errorMessage = String.format("Expected resource method: %s of class: %s to have the @RolesAllowed, @PermitAll, or @DenyAll annotation", method.getName(), method.getDeclaringClass().getName());
      boolean hasSomethingForRoles = method.isAnnotationPresent(javax.annotation.security.RolesAllowed.class) ||
        method.isAnnotationPresent(javax.annotation.security.PermitAll.class) ||
        method.isAnnotationPresent(javax.annotation.security.DenyAll.class);

      assertThat(hasSomethingForRoles).as(errorMessage).isTrue();
    }
  }
}
