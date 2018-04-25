package br.com.poc;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
/**
 * 
 * Declaração de filtro Zuul, filtros podem ser usados
 * para implementar cross cutting concerns como logging,
 * auditing, tracing, security, dynamic routing tudo de
 * forma centralizada sem impactar a implementação individual
 * de cada serviço,
 *
 */
@Component
public class PocZuulFilter extends ZuulFilter {

	//Condição para dizer se o filtro está ativado ou não.
	@Override
	public boolean shouldFilter() {
		return Boolean.TRUE;
	}
	
	//Tipo de filtro, valores válidos:
	//"pre" - Filtro executado antes do serviço de destino ser execudado e pode alterar a requisição
	//"route" - Filtro invocado antes do serviço de destino ser execudado e que tem
	//          a capacidade de mudar o destino da chamada
	//"post" - Filtro executado após a de destino ser execudado e pode alterar a resposta
	//"error" - Tipo de filtro para error handling
	@Override
	public String filterType() {
		return "pre";
	}

	//Ordem de execução do filtro dentre os filtros de mesmo tipo definidos
	//caso se tenha mais de um.
	@Override
	public int filterOrder() {
		return 1;
	}

	//Método que é chamado pelo filtro para agir sobre a requisição,
	//é aqui que a logica deve ser implementada
	@Override
	public Object run() throws ZuulException {
		//Os dados da requisição são recuperados a partir desse objeto
		RequestContext currentContext = RequestContext.getCurrentContext();
		
		System.out.println("Requisição: "+currentContext.getRequest().getRequestURL()+" passou pelo filtro");
		
		//Alterando headers
		
		//O contexto zuul oferece o método abaixo para incluir headers customizados
		//na requisição
		currentContext.addZuulRequestHeader("algumHeaderRequest", "algumValor");
		//e na resposta
		currentContext.addZuulResponseHeader("algumHeaderResponse", "algumvalor");
		//Esse mapa de headers zuul é mergeado com os headers da requisição no momento
		//da chamada do serviço de destino e chega ao cliente como se o microservice
		//de destino tivesse adicionado
		//Não funciona tentar alterar os headers originais diretamente na requisição esses são read only.
		currentContext.getOriginResponseHeaders();
		
		return null;
	}

}

