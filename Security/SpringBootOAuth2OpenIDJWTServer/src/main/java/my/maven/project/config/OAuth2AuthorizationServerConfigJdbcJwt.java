package my.maven.project.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * curl -X POST --user 'system:system' -d 'grant_type=password&username=user1&password=user1' http://localhost:8081/spring-security-oauth-server/oauth/token
 * curl -X POST --user 'system:system' -d "grant_type=password&username=user1&password=user1" http://localhost:8080/SecurityAPI/spring-security-oauth-server/oauth/token
 * curl -X GET http://localhost:8081/spring-security-oauth-server/tokens
 * 
 * @author andrea
 *
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfigJdbcJwt extends AuthorizationServerConfigurerAdapter /*implements AuthorizationServerConfigurer*/ {
	
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    
	@Autowired
	private DataSource dataSource;

    @Value("classpath:schema_oauth2.sql")
    private Resource schemaScript;

    @Value("classpath:data_oauth2.sql")
    private Resource dataScript;
    
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
        	.tokenKeyAccess("permitAll()")
        	.checkTokenAccess("isAuthenticated()")
//        	.passwordEncoder( passwordEncoder() ) //(NoOpPasswordEncoder.getInstance())
//        	.allowFormAuthenticationForClients() // DA PROVARE
        ;
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception { // @formatter:off
//        clients
//        	.inMemory()
////                .withClient("sampleClientId")
////                .authorizedGrantTypes("implicit")
////                .scopes("read", "write", "foo", "bar")
////                .autoApprove(false)
////                .accessTokenValiditySeconds(3600)
////                .redirectUris("http://localhost:8083/","http://localhost:8086/")
////                .and()
////                .withClient("fooClientIdPassword")
////                .secret(passwordEncoder.encode("secret"))
////                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
////                .scopes("foo", "read", "write")
////                .accessTokenValiditySeconds(3600)       // 1 hour
////                .refreshTokenValiditySeconds(2592000)  // 30 days
////                .redirectUris("http://www.example.com","http://localhost:8089/","http://localhost:8080/login/oauth2/code/custom","http://localhost:8080/ui-thymeleaf/login/oauth2/code/custom", "http://localhost:8080/authorize/oauth2/code/bael", "http://localhost:8080/login/oauth2/code/bael")
////                .and()
////                .withClient("barClientIdPassword")
////                .secret(passwordEncoder.encode("secret"))
////                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
////                .scopes("bar", "read", "write")
////                .accessTokenValiditySeconds(3600)       // 1 hour
////                .refreshTokenValiditySeconds(2592000)  // 30 days
////                .and()
////                .withClient("testImplicitClientId")
////                .authorizedGrantTypes("implicit")
////                .scopes("read", "write", "foo", "bar")
////                .autoApprove(true)
////                .redirectUris("http://www.example.com")
////             .and()
////				.withClient("gigy")
////				.secret( passwordEncoder.encode("secret") ) // .secret("secret")
////				.accessTokenValiditySeconds(3600)//(10*60) //(expiration)
////				.refreshTokenValiditySeconds(30*60)
////				.scopes("read", "write")  // .scopes("account", "contacts", "internal")
////				.authorities("ROLE_ADMIN", "ROLE_USER", "ROLE_ANONYMOUS") // (Authorities.ROLE_ADMIN.name(), Authorities.ROLE_USER.name())
////				.authorizedGrantTypes("password", "refresh_token", "client_credentials")
////				.resourceIds("resource")  // .resourceIds()
////				.autoApprove(true)
////			.and()
//		        .withClient("system")
//		        .secret(passwordEncoder.encode("system"))
//		        .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
//		        .authorities("ROLE_ADMIN", "ROLE_USER", "ROLE_ANONYMOUS") // (Authorities.ROLE_ADMIN.name(), Authorities.ROLE_USER.name())
//		        .scopes("WEBAPP", "read", "write")
//		        .autoApprove(true)
//		        .accessTokenValiditySeconds(3600) // 1 hour //(10*60) //(expiration)
//		        .refreshTokenValiditySeconds(2592000) // 30 days
//		        .redirectUris("http://www.example.com","http://localhost:8089/","http://localhost:8080/login/oauth2/code/custom","http://localhost:8080/ui-thymeleaf/login/oauth2/code/custom", "http://localhost:8080/authorize/oauth2/code/bael", "http://localhost:8080/login/oauth2/code/bael")
//		        .resourceIds("resource")  // .resourceIds()
//		    ;    
        
		clients
    		.jdbc( dataSource )
    			.passwordEncoder( passwordEncoder )
//        		.usersByUsernameQuery("select email,password,true from calendar_users where email = ?")
//        		.groupAuthoritiesByUsername("select g.id, g.group_name, ga.authority from groups g, group_members gm, group_authorities ga where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id")
//        		.authoritiesByUsernameQuery("select cua.id, cua.authority from calendar_users cu, calendar_user_authorities cua where cu.email = ? and cu.id = cua.calendar_user")
    ;
        
    } // @formatter:on
    
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer())); // VALIDO PER JDBC E INMEMORY - ANCHE SENZA SI HA OAUTH2 TOKEN
		tokenEnhancerChain.setTokenEnhancers( Arrays.asList( tokenEnhancer(), jwtAccessTokenConverter() ) ); // tokenEnhancer OPZIONALE - VALIDO SOLO PER JWT - ABILITA JWT
		
        endpoints
        	.tokenStore(tokenStore()) // OPZIONALE
//        	.accessTokenConverter(accessTokenConverter()) // VALIDO SOLO PER INMEMORY, INIZIALMENTE DISABILITATO - NON FUNZIONA PER ABILITARE JWT
        	.tokenEnhancer(tokenEnhancerChain) // ABILITA JWT
        	.authenticationManager(authenticationManager) // OBBLIGATORIO SENZA DA: unsupported_grant_type
//			.reuseRefreshTokens(false) // OPZIONALE
//            .approvalStore(approvalStore()) // OPZIONALE
//            .authorizationCodeServices(authorizationCodeServices()) // OPZIONALE
        	
//        endpoints.userDetailsService(userDetailsService)
        	
        ;
        
    }
    
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore()); // OBBLIGATORIO
//        defaultTokenServices.setSupportRefreshToken(true); // OPZIONALE
        return defaultTokenServices;
    }
    
	@Bean
	public TokenEnhancer tokenEnhancer() {
//	      return new TokenEnhancerChain();
	    return new CustomTokenEnhancer();
	}
    
    
    // JDBC TOKEN STORE CONFIGURATION
	// NECESSARIO PER H2, COMMENTATO PER MYSQL ALTRIMENTI DA ERRORE DROPPANDO LE TABELLE IN FASE DI RICHIESTA TOKEN
//    @Bean
//    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
//        final DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        initializer.setDatabasePopulator(databasePopulator());
//        return initializer;
//    }
//
//    private DatabasePopulator databasePopulator() {
//        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScript(schemaScript);
//        populator.addScript(dataScript);
//        return populator;
//    }

//    @Bean
//    @Qualifier("dataSourceOAuth2")
//    public DataSource dataSource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//        dataSource.setUrl(env.getProperty("jdbc.url"));
//        dataSource.setUsername(env.getProperty("jdbc.user"));
//        dataSource.setPassword(env.getProperty("jdbc.pass"));
//        return dataSource;
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource());
//    }
    
    
    // JWT TOKEN STORE CONFIGURATION
    @Bean
    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource()); JdbcTokenStore(ds); // VALIDO PER JDBC E INMEMORY - implements token services that stores tokens in a database
        return new JwtTokenStore(jwtAccessTokenConverter()); // VALIDO PER JWT, ANCHE INMEMORY MA INIZIALMENTE DISABILITATO - NON ABILITA JWT
//        return new InMemoryTokenStore();
//        return new JwkTokenStore(); // DA STUDIARE IL COSTRUTTORE
//        return new RedisTokenStore(); // DA VEDERE LE DIPENDENZE E STUDIARE IL COSTRUTTORE
    }
    
    // VALIDO PER JWT, ANCHE INMEMORY MA INIZIALMENTE DISABILITATO
    @Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
    	
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        
//        converter.setSigningKey("123");
        
		converter.setSigningKey(privateKey);
		converter.setVerifierKey(publicKey);
        
//        converter.setSigningKey( "private key" );
////        converter.setVerifierKey( "public key" );
////        Error creating bean with name 'accessTokenConverter' defined in class path resource [my/maven/project/config/OAuth2AuthorizationServerConfigJdbcJwt.class]: Invocation of 
////        init method failed; nested exception is java.lang.IllegalStateException: For MAC signing you do not need to specify the verifier key separately, and if you do it must 
////        match the signing key
        
        // IL TOKEN HA UNA LUNGHEZZA MAGGIORE ED E' PIU' SICURO
//        final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
//        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        

//        private final SecurityProperties securityProperties;
//        private JwtAccessTokenConverter jwtAccessTokenConverter;
//        private final SecurityProperties securityProperties;
//        SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();
//        KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));
//
//        jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setKeyPair(keyPair);
        
        return converter;
        // DA STUDIARE IL DefaultAccessTokenConverter
    }
    
	private String publicKey = "-----BEGIN PUBLIC KEY-----\r\n"+
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyjMsAVNpTLl69Kn7qQ5L\r\n"+
			"CiwbvBiASASHHvvoIMct/eXWcO+lVEnUjIDKSDtjr6Z1IBz/ZdN32ptvM93hxL48\r\n"+
			"QFiK+cr1QFLS/UjnqjrniCQYZJmbyXXbWi/8/AnrYERn6NdINx34nnpIEWvhvLX9\r\n"+
			"El+houbhNssBF61ktRzd0y4tL3msnW1EKK1cQ9SWWQzqvzcRntSRRiBaCWmixsHL\r\n"+
			"EleGNLg5KfmEM0YkOhWH5tuwPAnTi+FGNebnh0O58PA8kFTMymYyPIgZg9jLLsys\r\n"+
			"vwk0OVavoeLWQ47ZVIf/IIx3w9pj+CKbk7sGkBblY6iQpypAv8p83zUw0jPguC8y\r\n"+
			"TwIDAQAB\r\n"+
			"-----END PUBLIC KEY-----";

	private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\r\n"+
			"MIIEpAIBAAKCAQEAyjMsAVNpTLl69Kn7qQ5LCiwbvBiASASHHvvoIMct/eXWcO+l\r\n"+
			"VEnUjIDKSDtjr6Z1IBz/ZdN32ptvM93hxL48QFiK+cr1QFLS/UjnqjrniCQYZJmb\r\n"+
			"yXXbWi/8/AnrYERn6NdINx34nnpIEWvhvLX9El+houbhNssBF61ktRzd0y4tL3ms\r\n"+
			"nW1EKK1cQ9SWWQzqvzcRntSRRiBaCWmixsHLEleGNLg5KfmEM0YkOhWH5tuwPAnT\r\n"+
			"i+FGNebnh0O58PA8kFTMymYyPIgZg9jLLsysvwk0OVavoeLWQ47ZVIf/IIx3w9pj\r\n"+
			"+CKbk7sGkBblY6iQpypAv8p83zUw0jPguC8yTwIDAQABAoIBACYwiA0wDeFZ3uk6\r\n"+
			"+bcyZeXj8tER55iykjq95Vfbhso/kML/4EANOcHXyVzfKrLQQ6rvDyXSTP7TAKvR\r\n"+
			"KoUYURTuJEGNnciqXANOs8KpIXXLK3hEsvHX+rgL/EI2NWiXQvDtSExwsrZr7hSs\r\n"+
			"RZR44vWHbmXwKXVJ3kj0jOLQMu1ho0rMqG0EUokL5y+8FS8aSuA3G0pChqGG/xIN\r\n"+
			"idfCFsDkTGXoAR+BGEA0P2jpn3eALfWUGuhIS2o9mUv4aBvbYPhip++dB/80h5bW\r\n"+
			"SthPRKstRIvGA6pfebmd1YCaJ5Ww9TX3dNS12d7qf4RgPOsWbzst9fy8hzxD/3uE\r\n"+
			"QDHd/6ECgYEA7iOxa6z1XPZRNrCaLh2E4Gqz8FKhZtc8P90ziI8VVD3X6+uJUWR3\r\n"+
			"p448edCRCkxJkbLV1IYefTr2SiYtGb4teH3fB5tAhHW1izyfGDZM5n+ObbHb8z/T\r\n"+
			"75/xpok01Oadg7upTd5t3T9n8dFT2Xt0o1Oe8eqzZyJ0G2vwY1mUCecCgYEA2V1v\r\n"+
			"a6Imwbv3yylaJ84C2YKsq2IAmdMU5iglJTwZ3FD7QOUIijeK4l4MHTa+L2c0UUtS\r\n"+
			"WNtBZ9jgaOXKD1ZNhifPusvasci23fU0Hx/Vax1Qge04+qs0/rCxsRGRShRdKzvC\r\n"+
			"LHmIGZptqz/niz0Nnh9xTrGhfQwlpNvdvbAMF1kCgYEAnd8txLth4mItTvtfC0lo\r\n"+
			"iLpUVfMBq0LvX0tO2a45rqJdHbsFSt827+68qukY7mHKt/t+BKlxVwYatud+KL3K\r\n"+
			"OUIA9HL5H5dFZmwm8I83Bev0SUaLEUT0RLydIBF/49CLBgUH5WabIPzi9Q4X72sH\r\n"+
			"1SsfiTNWAf52SKOTNtnw6WsCgYBq7ElY2uTRvBMCfw74MaC5OkyR6Z1+DZVkOi87\r\n"+
			"h7r442UU4RU4WGYbQEQZQsp/KvdTXgfd6czctpR9RTwGG1/7NC49JvYnKiK6QFop\r\n"+
			"TVGAZWUq5HR46ishde2Sup+Ln0TNdZHoqsfGQG7eJItTtO6z76efHEXh603BMN58\r\n"+
			"5tKr0QKBgQCKBGoLpZO9EwK2L887MaWNFIHJZWRsQpOs8++Y9MI345khJSW1Lir6\r\n"+
			"gK0ibkW9xW0Ut7K/nbeeryJDlouwTe+U+mfXAI/PAQfbbWYG9KXbH+NIIt8g2i6z\r\n"+
			"i4tmSFuYP5Sfcbx7QXcJ6jJhAviFT2qQtXtL8TXVmj6h34o4Auf7EQ==\r\n"+
			"-----END RSA PRIVATE KEY-----";
    
	@Bean
	public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
	    return new OAuth2AccessDeniedHandler();
	}
    
//	@Bean
//	public ApprovalStore approvalStore() {
//	    return new JdbcApprovalStore(/* oauthDdataSource() */dataSource);
//	}
	
//	@Bean
//	public AuthorizationCodeServices authorizationCodeServices() {
//		return new JdbcAuthorizationCodeServices(/* oauthDdataSource() */dataSource);
//	}

   
	
//    https://blog.marcosbarbero.com/centralized-authorization-jwt-spring-boot2/
//    private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory) {
//        return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(), jwtProperties.getKeyPairPassword().toCharArray());
//    }
//
//    private KeyStoreKeyFactory keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties) {
//        return new KeyStoreKeyFactory(jwtProperties.getKeyStore(), jwtProperties.getKeyStorePassword().toCharArray());
//    }
    
//  @Bean
//  public UserDetailsService userDetailsService() {
//      return new InMemoryUserDetailsManager(
//
//          User.withDefaultPasswordEncoder()
//              .username("user")
//              .password("user")
//              .authorities("ROLE_USER")
//              .build()
//
//      );
//  }
    
}
