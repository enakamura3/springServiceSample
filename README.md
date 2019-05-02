# README

Projeto spring com exemplos

URLs:

```
http://localhost:9090/springServiceSample/cep/04304050
http://localhost:9090/springServiceSample/v2/api-docs
http://localhost:9090/springServiceSample/swagger-ui.html

```


## Web service

### profile

Este projeto utiliza o spring profile configurado para _dev_
Utilizar a configuração abaixo para inciar a aplicação.
-Dspring.profiles.active=dev

```shell
java -Dspring.profiles.active=dev springServiceSample-1.0.0.jar
```

Para gerar o pacote também, será necessário informar o profile:


```shell
mvn clean package -Dspring.profiles.active=dev

```

Isso é necessário porque a classe de teste é executada, e ela vai tentar subir o serviço.
Sem o profile, não é possível obter as informações do application.propeties.

Podemos também colocar a anotação _@ActiveProfiles_ com o profile que será utilizado na classe de teste:

```
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringServiceSampleApplicationTests {
```

Com isso não precisamos mais definir o _profile_ quando gerarmos os pacotes. 

```
mvn clean package
```



### context

Podemos definir um contexto, que seria um string na URL para acessar algum recurso.
Como assim?

Por padrão, quando criamos uma aplicação ela fica assim:

```
http://server:port/methodPath
```

Mas podemos mudar e colocar por exemplo, o nome do nosso serviço na URL de forma fixa como um contexto da URL.

```
http://server:port/serviceName/methodPath
```

E para isso configuramos no application.properties:

```
server.servlet.context-path=/meuServicoMaroto
```

É importante que ele comece com o caractere "/"

Ao iniciar o serviço ele vai mostrar no console:

```
Tomcat started on port(s): 9090 (http) with context path '/meuServicoMaroto'
```



### @RestTemplate

Serve para identificar a classe como um controller
É a junção de duas outras anotações: Controller e ResponseBody

### @GetMapping

Usamos ela em cima de um método que queremos que seja exposto através de uma URL
Podemos passar como parâmetro o path em da URL.

## Log

Para usarmos, podemos usar o as classes Logger e LoggerFactory do SLF4J.
Exemplo de uso:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static Logger LOGGER = LoggerFactory.getLogger(FooFactory.class);
```

No pom, é preciso adicionar as dependências:

```xml
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-api</artifactId>
	<version>1.7.26</version>
</dependency>
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-simple</artifactId>
	<version>1.7.26</version>
</dependency>
```

Isso evita o erro no console:

```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```

E também faz com que o logger seja executado e imprima o log no console.

Como estamos usando o spring boot adicionar estas dependencias não será necessário, mas se fosse um projeto sem spring boot,
teriamos que adicionar essas dependencias para o slf4j funcionar.


## actuator

importar a dependencia no maven:

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

importante o artifactId ser o __spring-boot-starter-actuator__, tentei usar o __spring-boot-actuator__ e o endpoint _/actuator_ não funcionou.
Talves por não ser o starter, precisa de mais configurações.

Onde fica a url?
Acessar seguindo o modelo: http://hostname:port/actuator

Acessando essa URL, ele vai apresentar as URL do Spring Actuator que estão disponíveis:

Por padrão são aparece:

```json
{
   "_links":{
      "self":{
         "href":"http://localhost:9090/actuator",
         "templated":false
      },
      "health-component":{
         "href":"http://localhost:9090/actuator/health/{component}",
         "templated":true
      },
      "health-component-instance":{
         "href":"http://localhost:9090/actuator/health/{component}/{instance}",
         "templated":true
      },
      "health":{
         "href":"http://localhost:9090/actuator/health",
         "templated":false
      },
      "info":{
         "href":"http://localhost:9090/actuator/info",
         "templated":false
      }
   }
}
```

## swagger

Podemos utilizar um projeto para gerar um swagger de nossas APIs

Importar no maven as duas dependências:

```xml
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
	<version>2.9.2</version>
</dependency>

<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>2.9.2</version>
</dependency>
```

O __springfox-swagger2__ gera um swager no formato json que pode ser aberto e convertido para YAML no swagger editor
O __springfox-swagger-ui__ gera uma página HTML com o swagger

Como usar?

__@EnableSwagger2__
O ideal é criar uma classe @Configuration para colocar esta notação, pois são necessárias algumas configurações para definir e personalizar o swagger gerado.
Se apenas colocarmos na classe Controller, vai mostrar mais operaçãoes (ApiOperation) que o necessário, incluindo os do spring actuator. 

Neste exemplo temos uma classe como Controller e outra como Configuração do Swagger.

* org.naka.demo.springServiceSample.ViaCep.java =  classe RestController com as operações 
* org.naka.demo.springServiceSample.config.SwaggerConfig.java = classe com configurações do swagger

Na classe SwaggerConfig usamos as anotações @EnableSwagger2 e @Configuration para criarmos as configurações através de um Bean que cria um objeto Docket.
Neste objeto docker colocamos as informações do swagger como descrição e pacotes das classes que contém as operações das APIs.

Na classe ViaCep temos as anotações @Api e @ApiOperation para podermos definir descrições e quais os métodos que serão apresentados no swagger.

Por padrão todos os métodos da classe controller são exibidos. Para não exibirmos precisamos anotar o método com a anotação @ApiIgnore

@Api = permite colocar uma definição na API do swagger
@ApiOperation = permite colocar uma definição na operação da API
@ApiIgnore = ignora o método na construção do swagger

____


Depois que configurar, podemos acessar o swagger através de duas URLs

1. http://server:port/v2/api-docs - Swagger em JSON
2. http://server:port/swagger-ui.html - Swagger em HTML





