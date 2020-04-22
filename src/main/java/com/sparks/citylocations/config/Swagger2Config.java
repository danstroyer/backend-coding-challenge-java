package com.sparks.citylocations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Swagger2Config
 * Class to define the initial configuration for swagger ui
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    /**
     *
     * @return
     */
    @Bean
    public LinkDiscoverers discovers() {

        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

    }

    /**
     * Method to define the Docket creation and map the beans into swagger
     * @return
     */
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api")
                .apiInfo(apiInfo()).select()
                .paths(regex("/.*"))
                .build();
    }

    /**
     * Method to define the API Information presented by Swagger
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Sparks REST API")
                .description("Cities Name Suggest API")
                .contact(new Contact("Daniel Farias", "#", "edfariasn@gmail.com"))
                .license("Sparks License")
                .version("1.0")
                .build();
    }
}
