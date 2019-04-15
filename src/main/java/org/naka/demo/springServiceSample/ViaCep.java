package org.naka.demo.springServiceSample;

import org.naka.demo.springServiceSample.model.CEP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"viacep"}) // colocamos a referencia da tag
@RestController
@RequestMapping("/cep")
public class ViaCep {

	@Value("${viaCepUrl}")
	private String viaCepUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// Exemplo de uso de log com SLF4J
	// Basta importar as classes Logger e LoggerFactory
	private static Logger LOGGER = LoggerFactory.getLogger(SpringServiceSampleApplication.class); 
	
	@ApiOperation(value = "Return information about a cep number") // informa uma descrição sobre o método
	@GetMapping("/{cepNumber}")
	public ResponseEntity<CEP> test(@PathVariable String cepNumber) {
		LOGGER.info("Searching info about cep number: {}", cepNumber);
		ResponseEntity<CEP> response = restTemplate.getForEntity(viaCepUrl, CEP.class, cepNumber);
		LOGGER.info("Response: {}", response.getBody());
		return response; 
	}
	 
	@ApiIgnore // com essa anotação conseguimos fazer com que esse método não seja exibida no swagger
	@ApiOperation(value = "método de teste, não serve pra nada")
	@GetMapping("/test")
	public void test(){
		LOGGER.info("Just a method for test");
	}
}
