package my.maven.project.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * QUESTA CLASSE PERMETTE DI CONFIGURARE LE RICHIESTE CHE VANNO AUTORIZZATE VERSO I MICROSERVIZI RISPETTO A QUELLE DI RICHESTA GENERAZIONE TOKEN CHE NON DEVONO
 * ESSERE AUTORIZZATE: IN CASO DI RICHIESTA SENZA IL TOKEN DI AUTORIZZAZIONE:
 * 
 * {"error":"unauthorized","error_description":"Full authentication is required to access this resource"}
 * 
 * IL CONTROLLO DEL TOKEN E' DELEGATO ALL'AUTORIZATION SERVER
 * 
 * @author andrea
 * 
 * @modify 10/09/2021
 * 
 * DIPENDENZA:
 * 		<!-- ROUTING AUTHORIZATION SERVER -->
 *        <dependency>
 *            <groupId>org.springframework.security.oauth.boot</groupId>
 *            <artifactId>spring-security-oauth2-autoconfigure</artifactId>
 *        </dependency>
 *
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerJWTRemoteConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(final HttpSecurity http) throws Exception {
    	http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		.and()
		    .authorizeRequests()
		    	.antMatchers("/oauth/**").permitAll()
		    	
		    	//IN CASO DI CHIAMATA CENTRALIZZATA PER LA GENERAZIONE DEL TOKEN:
		    	// curl -X POST --user 'fooClientIdPassword:secret' -d "grant_type=password&username=user1&password=user1" http://localhost:8080/SecurityAPI/spring-security-oauth-server/oauth/token
		    	//E' STATO NECESSARIO AGGIUNGERE QUESTA RIGA:
		    	//Finding route for path: /SecurityAPI/spring-security-oauth-server/oauth/token
		    	//route matched=ZuulRoute{id='SpringBootOAuth2OpenIDJWTServer', path='/SecurityAPI/**', serviceId='SpringBootOAuth2OpenIDJWTServer', url='null', stripPrefix=true, retryable=null, sensitiveHeaders=[], customSensitiveHeaders=false, }
//		    	.antMatchers("/SecurityAPI/spring-security-oauth-server/oauth/token").permitAll()
		    	.antMatchers("/SecurityAPI/spring-security-oauth-server/oauth/**").permitAll()
		    	.antMatchers("/SecurityAPI/spring-security-oauth-server/token/**").permitAll() // PER L'ACCESSO AL TokenController
		    	
		    	.antMatchers("/**").authenticated()
        ;

    }

    @Bean
    @Primary
    public RemoteTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://autorizationserver:8081/spring-security-oauth-server/oauth/check_token");
        tokenService.setClientId("fooClientIdPassword");
        tokenService.setClientSecret("secret");
        return tokenService;
    }

}
