//package my.maven.project.config;
//
////import java.net.URI;
////import java.util.HashSet;
////import java.util.Set;
//
////import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
////import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
////import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
////import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
////import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//
///**
// * Furthermore, it's important to note that Spring adds authorities to the principal based on the scopes it received from the provider, prefixed with “SCOPE_“. 
// * For example, the openid scope becomes a SCOPE_openid granted authority.
// * These authorities can be used to restrict access to certain resources, for example:
// * 
// * @author andrea
// *
// */
//@Configuration
//@EnableWebSecurity
//@EnableResourceServer
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	
//    protected void configure(HttpSecurity http) throws Exception {
//    	
//        http.antMatcher("/**").authorizeRequests()
//	    	.antMatchers("/", "/login**").permitAll()
//	    	.anyRequest().authenticated()
////	    .and()
////	    	.oauth2Login()
//	    ;
//    	
////        Set<String> googleScopes = new HashSet<>();
////        googleScopes.add("https://www.googleapis.com/auth/userinfo.email");
////        googleScopes.add("https://www.googleapis.com/auth/userinfo.profile");
//// 
////        OidcUserService googleUserService = new OidcUserService();
////        googleUserService.setAccessibleScopes(googleScopes);
//// 
////        http
////          .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
////          .oauth2Login(oauthLogin -> oauthLogin.userInfoEndpoint().oidcUserService(googleUserService))
//////          .oauth2Login(oauthLogin -> oauthLogin.permitAll())
////          .logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler()))
////          ;
//        
//    }
//    
////    @Autowired
////    private ClientRegistrationRepository clientRegistrationRepository;
////     
////    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
////        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);  
////
////        oidcLogoutSuccessHandler.setPostLogoutRedirectUri(URI.create("http://localhost:8080/DME_Template/home"));
////
////        return oidcLogoutSuccessHandler;
////    }
//    
//}
