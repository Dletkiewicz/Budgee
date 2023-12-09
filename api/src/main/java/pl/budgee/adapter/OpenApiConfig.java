package pl.budgee.adapter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

  @Configuration
  @OpenAPIDefinition(info = @Info(title = "Budgee API", version = "1.0", description = "Budgee Application API"),
      security = {
          @SecurityRequirement(name = "oauth2"),
          @SecurityRequirement(name = "clientCredentials"),
          @SecurityRequirement(name = "bearerAuth")
      }
  )
  public class OpenApiConfig {

    public static final String SCHEME_YEARMONTH = "YearMonth";
    public static final String SCHEME_YEAR = "Year";
    public static final String SCHEME_LOCALDATE = "LocalDate";
    public static final String SCHEME_INSTANT = "Instant";
    public static final String SCHEME_SORT = "Sort";

    private static final String STRING_TYPE = "string";

    @Bean
    public OpenApiCustomizer customizeOpenApi(OAuth2ResourceServerProperties props) {
      return openApi -> openApi.getComponents().addSchemas(SCHEME_YEARMONTH,
              new Schema<>().name(SCHEME_YEARMONTH).type(STRING_TYPE).pattern("^\\d{4}-\\d{2}$").example("2020-09"))
          .addSchemas(SCHEME_YEAR, new Schema<>().name(SCHEME_YEAR).type(STRING_TYPE).pattern("^\\d{4}$").example("2020"))
          .addSchemas(SCHEME_LOCALDATE,
              new Schema<>().name(SCHEME_LOCALDATE).type(STRING_TYPE).format("date").pattern("^\\d{4}-\\d{2}-\\d{2}$").example("2020-09-01")).addSchemas(SCHEME_INSTANT,
              new Schema<>().name(SCHEME_INSTANT).type(STRING_TYPE).format("instant").example("2021-12-07T21:53:39.015831Z")).addSchemas(SCHEME_SORT,
              new Schema<>().name(SCHEME_SORT).type(STRING_TYPE).description("Nazwa atrybutu sortowania").example("id"))
          .addSecuritySchemes("oauth2", new SecurityScheme().name("oauth2").type(SecurityScheme.Type.OAUTH2).flows(
              new OAuthFlows().password(new OAuthFlow().tokenUrl(props.getJwt().getIssuerUri() + "/protocol/openid-connect/token"))))
                  .addSecuritySchemes("clientCredentials",
                      new SecurityScheme().name("clientCredentials").type(SecurityScheme.Type.OAUTH2).flows(
                          new OAuthFlows().clientCredentials(new OAuthFlow().tokenUrl(props.getJwt().getIssuerUri() + "/protocol/openid-connect/token"))))
                  .addSecuritySchemes("bearerAuth",
                      new SecurityScheme().name("bearerAuth").type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer"));
    }
  }
