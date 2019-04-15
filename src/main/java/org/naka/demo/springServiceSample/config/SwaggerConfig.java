package org.naka.demo.springServiceSample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 // Habilita o suporte para o plugin do swagger 
public class SwaggerConfig {
	
	// bean com configuração do swagger que vai ser gerado
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).select() // tipo da documentação
				.apis(RequestHandlerSelectors.basePackage("org.naka.demo.springServiceSample")) // indica o path para o pacote das classes controller
				.build() // precisa fazer o build
				.apiInfo(apiInfo())
				.tags(new Tag("viacep", "Consulta de CEP usando a API do VIA CEP")); // substitui a anotação "description". Agora precisamos declarar todas as descrições como "tags" na Docket.
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Consulta de CEP")
				.description("Aplicação de exemplo para consume de serviço VIACEP")
				.version("1.0.0")
				.license("Apache License Version 2.0")
		        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.build();
	}
}
