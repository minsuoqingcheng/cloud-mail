package cn.nju.edu.se.config;

import cn.nju.edu.se.rest.EmailResource;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(EmailResource.class);
    }

    @Value("${spring.jersey.application-path:/}")
    private String apiPath;

    @PostConstruct
    public void init() {
        // Register components where DI is needed
        this.configureSwagger();
    }

    private void configureSwagger() {
        // Available at localhost:port/api/swagger.json
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);

        BeanConfig config = new BeanConfig();
        config.setConfigId("Mail");
        config.setTitle("Mail");
        config.setVersion("v1");
        config.setContact("zt");
        config.setSchemes(new String[]{"http", "https"});
        config.setBasePath(this.apiPath);
        config.setResourcePackage("cn.nju.edu.se");
        config.setPrettyPrint(true);
        config.setScan(true);
    }

}
