package br.com.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul tem endpoints para consultar configurações
 * mas não descobri como ativa-los nesta versão o se
 * eles foram removidos.
 * 
 * Tipos de configuração de rota Zuul:
 * 
 * 1 - Configuração automatica por service id no eureka, por padrão
 * sem configuração nenhuma o zuul cria rotas padrões para os serviços
 * presentes no eureka seguindo o padrão http://{hostZull}/{serviceId}/...
 * ex: http://localhost:5555/poc-spring-cloud-eureka-client-1/enhancedRestTemplate
 * chama o serviço de nome poc-spring-cloud-eureka-client-1 registrado no eureka
 * e passa pra ele o path param enhancedRestTemplate.
 *
 */
@EnableZuulProxy
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
