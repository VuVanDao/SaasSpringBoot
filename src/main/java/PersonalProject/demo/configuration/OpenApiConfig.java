package PersonalProject.demo.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${app.header-tenant:X-tenant-Id}")
    private String tenantHeaderName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SaaS Spring Boot API Documentation")
                        .version("1.0.0")
                        .description("API Documentation with JWT Authentication and Multi-tenant header support."))
                // Apply the security requirements globally to all API endpoints
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth")
                        .addList("tenantIdAuth"))
                .components(new Components()
                        // 1. Configure JWT Bearer security scheme
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("Enter JWT access token (The 'Bearer ' prefix is handled automatically by Swagger UI)."))
                        // 2. Configure Tenant ID Header security scheme
                        .addSecuritySchemes("tenantIdAuth", new SecurityScheme()
                                .name(tenantHeaderName)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("Enter the Tenant ID required for multi-tenant requests (e.g., 1).")));
    }

    /**
     * Customizes OpenAPI operations to remove the duplicate header input fields for "Authorization"
     * and the custom tenant ID header (e.g., "X-tenant-Id") from each individual endpoint parameter list.
     * Since these are configured globally under security schemes, removing them from individual
     * parameters makes the Swagger UI cleaner and allows values to be sent from the global "Authorize" config.
     */
    @Bean
    public OperationCustomizer customizeParameters() {
        return (operation, handlerMethod) -> {
            if (operation.getParameters() != null) {
                operation.getParameters().removeIf(parameter -> {
                    boolean isHeader = "header".equalsIgnoreCase(parameter.getIn());
                    if (isHeader) {
                        String name = parameter.getName();
                        return "Authorization".equalsIgnoreCase(name) || tenantHeaderName.equalsIgnoreCase(name);
                    }
                    return false;
                });
            }
            return operation;
        };
    }
}
