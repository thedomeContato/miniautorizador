package br.com.thedomeit.miniautorizador.config;

import javax.servlet.ServletContext;

import static springfox.documentation.spring.web.paths.RelativePathProvider.ROOT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathProvider(new CustomPathProvider(servletContext))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag("VRCARD", "These endpoints are used in VR Card Record Maintenance Operations.", 1));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("VRCARD API")
                .description("This service handles Transaction with Card Registration")
                .version("1.0.0")
                .build();
    }

    private static class CustomPathProvider extends AbstractPathProvider {
        ServletContext servletContext;
        String host = "";

        CustomPathProvider(ServletContext servletContext) {
            this.servletContext = servletContext;
        }

        @Override
        protected String applicationPath() {
            return host;
        }

        @Override
        protected String getDocumentationPath() {
            return ROOT;
        }
    }
        
    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .defaultModelsExpandDepth(-1)
                .build();
    }

}