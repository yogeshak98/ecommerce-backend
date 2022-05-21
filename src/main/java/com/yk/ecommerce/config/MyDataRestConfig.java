package com.yk.ecommerce.config;

import com.yk.ecommerce.entity.Product;
import com.yk.ecommerce.entity.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
    RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
    var unSupportedActions = new HttpMethod[]{HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.POST};

    // disable HTTP methods for Product - PUT, POST, DELETE
    config.getExposureConfiguration()
        .forDomainType(Product.class)
        .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)))
        .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)));

    // disable HTTP methods for ProductCategory - PUT, POST, DELETE
    config.getExposureConfiguration()
        .forDomainType(ProductCategory.class)
        .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)))
        .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)));
  }
  
}
