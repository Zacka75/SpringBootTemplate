package my.maven.project.apigateway.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PreFilter extends ZuulFilter {
	
	Logger log = LoggerFactory.getLogger(PreFilter.class);
	
	/**
	 * contains the functionality of the filter.
	 * 
	 * This is run for each request received. Here we can see the contents of the request and handle it if necessary
	 * 
	 */
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		
		HttpServletRequest servletRequest = ctx.getRequest();
		
		log.info(String.format("%s request to %s", servletRequest.getMethod(), servletRequest.getRequestURL().toString()));

		HttpServletResponse servletResponse = ctx.getResponse();
		
		log.info(String.format("Location = %s -- location = %s", servletResponse.getHeader("Location"), servletResponse.getHeader("location")));

		return null;
	}
	
	/**
	 * contains the logic that determines when to execute this filter.
	 * 
	 * if true the filter will always be executed
	 * 
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}
	
	/**
	 * gives the order in which this filter will be executed, relative to other filters.
	 * 
	 */
	@Override
	public int filterOrder() {
		return 1;//FilterConstants.SEND_RESPONSE_FILTER_ORDER; // SIMPLE_HOST_ROUTING_FILTER_ORDER
	}
	
	/**
	 * filterType() returns a String that stands for the type of the filter
	 * 
	 * specifies when the filter is executed. If it returns “pre”, it is executed before they have made the redirect and therefore before it has been 
	 * called the end server (to Google, in our example). If it returns “post”, is executed after the server has responded. 
	 * In the org.springframework.cloud.netflix.zuul.filters.support.FilterConstantsclass, we have ve defined the types to be returned: 
	 * - PRE_TYPE : filters are executed before the request is routed
	 * - POST_TYPE : filters are executed after the request has been routed
	 * - ERROR_TYPE : filters execute if an error occurs in the course of handling the request
	 * - ROUTE_TYPE : filters can handle the actual routing of the request
	 * 
	 */
	@Override
	public String filterType() {
		return "pre";//FilterConstants.ROUTE_TYPE;
	}

}
