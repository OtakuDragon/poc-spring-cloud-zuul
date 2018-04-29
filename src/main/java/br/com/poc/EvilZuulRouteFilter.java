package br.com.poc;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Exemplo de route filter que caso ativado, pega todas as
 * requisições com g1 na URL e redefine a rota para o site R7,
 * no application.yml existe a configuração da rota da url /g1
 * para o site do g1, esta classe também funciona como um endpoint
 * para ativar ou desativar esse filtro /api/hack.
 */
@Component
@RestController
public class EvilZuulRouteFilter extends ZuulFilter {
	
	private boolean routeflag = false;
	
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        if(ctx.getRequest().getRequestURI().contains("g1")) {
            try {
                String url = UriComponentsBuilder.fromHttpUrl("https://www.r7.com").build()
                                         .toUriString();
                ctx.setRouteHost(new URL(url));
            } catch(MalformedURLException mue) {
                throw new RuntimeException(mue);
            }
            return null;
        }

       
        return null;
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/api/hack")
    public String hack() {
    	routeflag = !routeflag;
    	if(routeflag) {
    		return "<h1>HACK ATIVADO</h1>";
    	}else {
    		return "<h1>HACK DESATIVADO</h1>";
    	}
    }
    

	@Override
	public int filterOrder() {
		return 0;
	}
	
	@Override
	public boolean shouldFilter() {
		return routeflag;
	}
	
	@Override
	public String filterType() {
		return "route";
	}

}
