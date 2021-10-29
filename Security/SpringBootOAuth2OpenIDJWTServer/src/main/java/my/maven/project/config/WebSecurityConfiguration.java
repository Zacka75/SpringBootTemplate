package my.maven.project.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * curl -X POST --user 'fooClientIdPassword:secret' -d "grant_type=password&username=user1&password=user1" http://localhost:8081/spring-security-oauth-server/oauth/token
 * 
 * curl -X POST --user 'system:system' -d 'grant_type=password&username=user1&password=user1' http://localhost:8081/SpringSecurityOAuth2Server/oauth/token
 * 
 * @author andrea
 *
 */
@Configuration
//@Order(30)
@PropertySource({ "classpath:persistence.properties" })
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, /*jsr250Enabled = true,*/ securedEnabled = true) //<global-method-security pre-post-annotations="enabled" />
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private Environment env; 

//    @Value("${user.oauth.user.username}")
//    private String username;
//    @Value("${user.oauth.user.password}")
//    private String password;
	
	@Autowired
	DataSource dataSource;
	
//	@Autowired
//	UserDetailsService userDetailsService;
	
//    @Value("classpath:schema_user.sql")
//    private Resource schemaScript; // VALIDO SOLO PER JDBC E INMEMORY, NON PER JWT
//
//    @Value("classpath:data_user.sql")
//    private Resource dataScript; // VALIDO SOLO PER JDBC, NON PER INMEMORY, NON PER JWT
	
//	/**
//	 * Constructor disables the default security settings
//	 */
//	public WebSecurityConfiguration() {
//		super(true);
//	}
//	
//    public WebSecurityConfiguration(final DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	      
		http
//			.requestMatchers()
//      		.antMatchers("/login", "/oauth/authorize")
			.authorizeRequests()
//				.antMatchers("/login").permitAll()
				.antMatchers("/oauth/**").permitAll()
				
////				.antMatchers("/findById/**").permitAll()
//				.antMatchers("/api/user/me/**").permitAll()
//				.antMatchers("/users/extra/**").permitAll()
//				.antMatchers("/oauth/token/revoke/**").permitAll()
				.antMatchers("/oauth/token/revokeById/**").permitAll()
				.antMatchers("/tokens/**").permitAll()
				.antMatchers("/tokens/revokeRefreshToken/**").permitAll()
		    	
				.antMatchers("/**").authenticated()
			    .anyRequest().authenticated()
//		.and().formLogin().permitAll()
			.and().csrf().disable() // tokens at position 4 of 11 in additional filter chain; firing Filter: 'CsrfFilter'
									// Invalid CSRF token found for http://localhost:8081/spring-security-oauth-server/tokens
									// Not injecting HSTS header since it did not match the requestMatcher org.springframework.security.web.header.writers.HstsHeaderWriter$SecureRequestMatcher@3d5a8196
		;

//        http
//        	.antMatcher("/**").authorizeRequests()
//        	.antMatchers("/oauth/authorize**", "/login**", "/error**").permitAll();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
//		auth
//			.inMemoryAuthentication()
//				.withUser("user1").password(passwordEncoder().encode("user1")).roles("USER")
//				//.withUser(username).password(passwordEncoder().encode(password)).roles("USER")
//			.and()
//				.withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
//	        ;
		
    	// select username,password,enabled from users where username = ?
    	// select username,authority from authorities where username = ?
    	// select g.id, g.group_name, ga.authority from groups g, group_members gm, group_authorities ga where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id
    	// BCryptPasswordEncoder(4) is used for users.password column
        JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> cfg = auth
        	.jdbcAuthentication()
        		.dataSource(dataSource())
//        		.usersByUsernameQuery("select email,password,true from calendar_users where email = ?")
//        		.groupAuthoritiesByUsername("select g.id, g.group_name, ga.authority from groups2 g, group_members gm, group_authorities ga where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id")
//        		.authoritiesByUsernameQuery("select cua.id, cua.authority from calendar_users cu, calendar_user_authorities cua where cu.email = ? and cu.id = cua.calendar_user")
        		.passwordEncoder(passwordEncoder()  );
        
        // select username,password,enabled from users where username = ? -> ALMENO UNA QUERY SU GROUPS O AUTHORITIES DEVE ESSERE EFFETTUTA:
//        cfg.getUserDetailsService().setEnableGroups(true); // DEFAULT:false - CON true AGGIUNGE UNA QUERY ANCHE SU GROUPS, GROUP_MEMBERS, GROUP_AUTHORITIES -> select g.id, g.group_name, ga.authority from groups g, group_members gm, group_authorities ga where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id
//        cfg.getUserDetailsService().setEnableAuthorities(false); // DEFAULT:true - CON false NON EFFETTA LA QUERY SU AUTHORITIES -> select username,authority from authorities where username = ?      
		
//		auth.userDetailsService( userDetailsService );
//        auth.userDetailsService(userDetailsService());
    }
    
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//   	super.configure(web);
//   
//   	web.debug(true);
//   
//		web.ignoring().antMatchers("/login");
//	}

//    private PasswordEncoder passwordEncoder;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
//        if (passwordEncoder == null) {
//            passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        }
//        return passwordEncoder;
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    // NECESSARIO PER H2, DISABILITATO AUTOMATICAMENTE PER MYSQL: DataSourceInitializer       : Initialization disabled (not running DDL scripts)
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));
        return dataSource;
    }

//  @Autowired
//  public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
//
//		auth
//			.inMemoryAuthentication()
//				.withUser("user2").password(passwordEncoder.encode("user2")).roles("USER")
//			.and()
//				.withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN")
////			.and()	
////				.withUser("john").password(passwordEncoder.encode("123")).roles("USER")
////			.and()
////				.withUser("tom").password(passwordEncoder.encode("111")).roles("ADMIN")
//		;
//  }
    
    private UserDetailsService userDetailsService;
    
    @Bean
    public UserDetailsService userDetailsService() {
        if (userDetailsService == null) {
            userDetailsService = new JdbcDaoImpl();
            ((JdbcDaoImpl) userDetailsService).setDataSource(dataSource);
        }
        return userDetailsService;
    }

}
