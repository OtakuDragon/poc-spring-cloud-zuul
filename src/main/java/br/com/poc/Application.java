package br.com.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Endpoints de configuração Zuul
 * 
 * Zuul tem endpoints para consultar configurações
 * /routes para ver todas as rotas e /filters para ver
 * todos os filtros, eles devem ser ativados no application.yml
 * checar application.yml pra ver exemplo.
 * 
 * ---------------------------------------------------------------------------------------------------
 * Tipos de configuração de rota Zuul:
 * 
 * 1 - Configuração automatica por service id no eureka
 * 
 * Por padrão sem configuração nenhuma o zuul cria rotas padrões para os serviços
 * presentes no eureka seguindo o padrão http://{hostZull}/{serviceId}/...
 * ex: http://localhost:5555/poc-spring-cloud-eureka-client-1/enhancedRestTemplate
 * chama o serviço de nome poc-spring-cloud-eureka-client-1 registrado no eureka
 * e passa pra ele o path param enhancedRestTemplate.
 * 
 * A propriedade zuul: ignored-services: '*' desativa essa funcionalidade para 
 * que apenas rotas customizadas sejam definidas. 
 *
 * 2 - Configuração manual por service id
 * 
 * Nesse tipo de configuração são declarados os endpoints
 * para um service id manualmente no application.yml, Ver anotações no application.yml.
 * 
 * 3 - Configuração manual de serviços não Spring Boot
 * 
 * O Zuul também suporta a configuração de rotas para urls fixas
 * para configuração de serviços fora do eureka, isso não é recomendado
 * a forma correta de configurar um serviço non-Jvm ou non-Spring Boot
 * no Zuul e encaixar ele no eureka através da biblioteca Spring Cloud
 * Sidecar conforme o link abaixo e criar uma rota para o service id
 * dele com as opções 1 ou 2.
 * 
 * https://cloud.spring.io/spring-cloud-netflix/multi/multi__polyglot_support_with_sidecar.html
 * 
 * ---------------------------------------------------------------------------------------------------
 * Alterando rotas dinamicamente(spring cloud config server)
 * 
 * É possivel e recomendado que seja criado no spring cloud config server
 * uma pasta para o services gateway zuul e que todas as configurações de rotas fiquem
 * em um repositório GIT ao invés de fixas no código fonte do services gateway zuul,
 * dessa forma é possivel criar ou alterar rotas no repositório, fazer commit,
 * push e chamar o endpoint /refresh em todas as intancias do services gateway zuul,
 * dessa forma todas as alterações de rotas passam a executar sem necessidade
 * de gerar uma nova versão do services gateway zuul ou nem mesmo re-iniciar as instâncias.
 * 
 * ----------------------------------------------------------------------------------------------------
 * Configuração Hystrix e Ribbon do services gateway
 * 
 * O Zuul vem com funcionalidades Hystrix e Ribbon ativadas
 * e com comportamento padrão, para customizar essas funcionalidades
 * é possivel incluir propriedades no application.yml, não consegui
 * encontrar exemplos que funcionem dessas propriedades nessa versão.
 */
@EnableZuulProxy
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
