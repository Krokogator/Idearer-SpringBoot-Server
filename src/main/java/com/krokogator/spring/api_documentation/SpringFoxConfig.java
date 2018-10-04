package com.krokogator.spring.api_documentation;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SpringFoxConfig  {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                //All default 401, 403 etc responses
                .useDefaultResponseMessages(false)
                //Display apiInfo (title, description etc.)
                .apiInfo(apiInfo())
                .select()
                //Hides error controller
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.security.oauth2")))
                //Which paths to scan
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Idearer API")
                .description("Developer access only")
                //.termsOfServiceUrl("http://www.cool-message-receiver.com")
                .contact(new Contact("Michał Szczepanowski", null, "mm.szczepanowski@gmail.com"))
                //.license("Cool Proprietary Software")
                //.licenseUrl("www.cool-message.com")
                .version("0.1.0")
                .build();
    }


    private Predicate<String> paths(){
        return Predicates.not(PathSelectors.regex("/basic-error-controller.*"));
    }
}