package com.krokogator.spring.api_documentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerRedirectController {
    @RequestMapping("/")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui.html";
    }
}
