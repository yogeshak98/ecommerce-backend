package com.yk.ecommerce.config;

import com.yk.ecommerce.entity.Product;
import com.yk.ecommerce.entity.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

  private final EntityManager entityManager;

  @Autowired
  public MyDataRestConfig(EntityManager theEntityManager){
    this.entityManager = theEntityManager;
  }

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
    RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
    var unSupportedActions = new HttpMethod[]{HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.POST};

    // disable HTTP methods for Product - PUT, POST, DELETE
    config.getExposureConfiguration()
        .forDomainType(Product.class)
        .withItemExposure(((metadata, httpMethods) -> httpMethods.disable(unSupportedActions)))
        .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(unSupportedActions)));

    // disable HTTP methods for ProductCategory - PUT, POST, DELETE
    config.getExposureConfiguration()
        .forDomainType(ProductCategory.class)
        .withItemExposure(((metadata, httpMethods) -> httpMethods.disable(unSupportedActions)))
        .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(unSupportedActions)));

    // call an internal helper method to expose ids
    exposeIds(config);
  }

  private void exposeIds(RepositoryRestConfiguration config) {
    // egt a list of all entity classes from entity manager
    Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
    var d = new Class[entities.size()];

    var i = 0;
    for (var tempEntityType: entities){
      d[i++] = tempEntityType.getJavaType();
    }

    // expose entity ids for array of entity/domain types
    config.exposeIdsFor(d);
  }
}
