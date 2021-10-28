package my.maven.project.apigateway.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
 
public class ErrorFilter extends ZuulFilter {
	
	Logger log = LoggerFactory.getLogger(ErrorFilter.class);
 
    @Override
    public String filterType() {
    	return "error";
    }

    @Override
    public int filterOrder() {
    	return 1;
    }

    @Override
    public boolean shouldFilter() {
    	return true;
    }
 
    @Override
    public Object run() {

    	RequestContext ctx = RequestContext.getCurrentContext();

    	HttpServletRequest servletRequest = ctx.getRequest();

    	log.info(String.format("%s request to %s", servletRequest.getMethod(), servletRequest.getRequestURL().toString()));

    	HttpServletResponse servletResponse = ctx.getResponse();

    	log.info(String.format("Location = %s -- location = %s", servletResponse.getHeader("Location"), servletResponse.getHeader("location")));

    	return null;
    }
    
}