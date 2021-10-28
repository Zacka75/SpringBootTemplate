package my.maven.project.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import my.maven.project.apigateway.filters.ErrorFilter;
import my.maven.project.apigateway.filters.PostFilter;
import my.maven.project.apigateway.filters.PreFilter;
import my.maven.project.apigateway.filters.RouteURLFilter;

@Configuration
public class ZuulFilterConfig {
	
    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }
    @Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }
    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }
    @Bean
    public RouteURLFilter routeFilter() {
        return new RouteURLFilter();
    }

}
