package my.maven.project.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * You have two options: 
 * 1) Don't use the netflix DiscoveryClient and pass the serviceId as a logical hostname to ribbon (http://TEST/myservice), 
 * 2) Don't use the autowired RestTemplate, create a new one for your class. My choice would be #1.
 * 
 * If you use @EnableZuulServer (instead of @EnableZuulProxy), you can also run a Zuul server without proxying or selectively switch on parts of the  * proxying platform. 
 * Any beans that you add to the application of type ZuulFilter are installed automatically (as they are with @EnableZuulProxy) but without any of the proxy filters being added 
 * automatically.
 * 
 * In that case, the routes into the Zuul server are still specified by configuring "zuul.routes.*", but there is no service discovery and no proxying. 
 * Consequently, the "serviceId" and "url" settings are ignored.
 * 
 * 
 * @author andrea
 * 
 * @modify 10/09/2021
 *
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
}
