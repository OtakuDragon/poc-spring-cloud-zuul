package br.com.poc;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 
 * Exemplo de filtro que seta correlation id em todas
 * as requisições, caso a requisição ja contenha correlation id
 * significa qu ela é uma "sub" requisição então o filtro propaga
 * o correlation id existente para a proxima requisição.
 * 
 * Correlation id é um numero único compartilhado
 * por todas as "sub" requisições de uma execução de microservice
 * e é utilizado para fazer o tracing da execução.
 * 
 * A poc poc-spring-cloud-correlation-id mostra o lado cliente dessa
 * estrutura.
 *
 */
@Component
public class CorrelationIdZuulFilter extends ZuulFilter{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Object run() throws ZuulException {
		RequestContext context = RequestContext.getCurrentContext();
		
		String correlationId = context.getRequest().getHeader("correlation-id");
		
		if(correlationId != null) {
			logger.info("Zuul: processando requisição com correlation-id: " + correlationId.toString());
		}else {
			correlationId = UUID.randomUUID().toString();
			logger.info("Zuul: criando correlation id e setando na requisição: " + correlationId.toString());
			RequestContext.getCurrentContext().addZuulRequestHeader("correlation-id", correlationId.toString());
		}
		
		
		return null;
	}
	
	@Override
	public boolean shouldFilter() {
		return Boolean.TRUE;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 2;
	}

}
