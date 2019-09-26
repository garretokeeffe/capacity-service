package ie.gov.agriculture.fisheries.la.capacityservice.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.base.Predicates;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationCodeGrant;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    	
	@Value("${keycloak.auth-server-url}")
	private String AUTH_SERVER;

	@Value("${keycloak.credentials.secret}")
	private String CLIENT_SECRET;

	@Value("${keycloak.resource}")
	private String CLIENT_ID;

	@Value("${keycloak.realm}")
	private String REALM;

	private static final String GROUP_NAME = "capcity-service";
	private static final String TITLE = "API Documentation for capcity-services";
	private static final String DESCRIPTION = "Capcity Rest services for retrieving details by sfos/capacity/*";
	private static final String VERSION = "1.0.0";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName(GROUP_NAME)
				.useDefaultResponseMessages(true)
				.apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("ie.gov.agriculture.fisheries.la"))
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(buildSecurityScheme())
				.securityContexts(buildSecurityContext());
				//.securityContexts(Arrays.asList(securityContext()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(TITLE)
				.description(DESCRIPTION)
				.contact(new Contact("Stephen McCosker", "", "stephen.mccosker@agriculture.gov.ie")).version("1.0.0")
				.version(VERSION).build();
	}

	@Bean
	public SecurityConfiguration security() {
		Map<String, Object> additionalQueryStringParams=new HashMap<>();
		return SecurityConfigurationBuilder
				.builder()
				.realm(REALM)
				.clientId(CLIENT_ID)
				.clientSecret(CLIENT_SECRET)
				.additionalQueryStringParams(additionalQueryStringParams)
				.appName(GROUP_NAME)
				.scopeSeparator(" ")
				.build();
	}

	private List<? extends SecurityScheme> buildSecurityScheme() {
		List<SecurityScheme> secSchemes = new ArrayList<>();

		GrantType grantType = new AuthorizationCodeGrant(new TokenRequestEndpoint( AUTH_SERVER + "/realms/" + REALM + "/protocol/openid-connect/auth", CLIENT_ID, CLIENT_SECRET),
				new TokenEndpoint(AUTH_SERVER + "/realms/" + REALM + "/protocol/openid-connect/token", GROUP_NAME));
				

		SecurityScheme oauth = new OAuthBuilder()
				.name(REALM)
				.grantTypes(Arrays.asList(grantType))
				.scopes(Arrays.asList(scopes()))
				.build();

		secSchemes.add(oauth);

		return secSchemes;
	}
	  
	private AuthorizationScope[] scopes() {
		AuthorizationScope[] scopes = { 
				new AuthorizationScope("openid", "for sfos service")
			};
		return scopes;
	}

	
	private List<SecurityContext> buildSecurityContext() {
		List<SecurityReference> securityReferences = new ArrayList<>();

		securityReferences.add(
				SecurityReference
					.builder()
					.reference(REALM)
					.scopes(scopes())
					.build()
					);

		SecurityContext context = SecurityContext.builder().forPaths(Predicates.alwaysTrue()).securityReferences(securityReferences).build();

		List<SecurityContext> ret = new ArrayList<>();
		ret.add(context);
		return ret;
	}
}
