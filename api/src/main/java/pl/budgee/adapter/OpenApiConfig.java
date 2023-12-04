package pl.budgee.adapter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Budgee Api", version = "1.0", description = "Budgee Api version 1.0"))
public class OpenApiConfig {

}
