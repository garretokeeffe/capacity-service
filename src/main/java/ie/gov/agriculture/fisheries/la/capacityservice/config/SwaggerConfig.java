package ie.gov.agriculture.fisheries.la.capacityservice.config;

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
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          	.apis(RequestHandlerSelectors.any())              
          	  .paths(PathSelectors.any())                          
          	    .build()
          	    .apiInfo(apiInfo());
    }
    
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Customer Capacity services API")
				.description("Customer Capacity Rest services for retrieving capacity information for an individual customer, vessel etc.")
				.contact(new Contact("Stephen McCosker", "", "stephen.mccosker@agriculture.gov.ie")).version("1.0.0")
				.build();
	}
}