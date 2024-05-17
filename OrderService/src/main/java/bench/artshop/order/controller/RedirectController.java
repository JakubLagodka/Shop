package bench.artshop.order.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden
public class RedirectController {
    @GetMapping
    public String redirectToOpenApi() {
        return "redirect:/swagger-ui.html";
    }
}
