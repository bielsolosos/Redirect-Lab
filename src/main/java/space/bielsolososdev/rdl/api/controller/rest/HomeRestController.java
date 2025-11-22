package space.bielsolososdev.rdl.api.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import space.bielsolososdev.rdl.api.model.HealthStatusResponse;



@RestController
@RequestMapping("rest")
public class HomeRestController {

    @GetMapping()
    public HealthStatusResponse getHealthStatus() {
        return new HealthStatusResponse("API REST FUNCIONANDO NORMALMENTE", true);
    }
    
}
