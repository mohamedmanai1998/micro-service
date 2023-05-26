package tn.esprit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService userDetailsService;
	
	/**
	 * 
	 */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 *
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	/**
	 *
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/topic/notification",
				"/runtime.js", "/scripts.js", "/styles.js", "/polyfills.js", "/assets/**",
				"/glyphicons-halflings-regular.woff2", "/glyphicons-halflings-regular.woff",
				"/materialdesignicons-webfont.woff2", "/glyphicons-halflings-regular.ttf", "/vendor.js", "/main.js",
				"/login", "/index.html", "/fontawesome-webfont.woff", "/js/**", "/img/**", "/css/**", "/static/img/**",
				"static/**", "/accounts/login","/accounts/register","/accounts/updateActive","/accounts/**", "/fontawesome-webfont.ttf", "/fontawesome-webfont.woff2").permitAll();
		http.authorizeRequests()
				.antMatchers("/static/img/**", "/api/account/reset_password/init/**",
						"/api/account/reset_password/finish/**", "/api/startProcess", "/api/confirmation/**",
						"/websocket/Systemalerte/**", "/api/annulation/**", "/login", "/index.html", "/js/**",
						"/img/**", "/css/**", "/ws/**")
				.permitAll();
		http.authorizeRequests().anyRequest().authenticated();
//		http.addFilter(new JWTAuthentificationFilter(authenticationManager()));
		http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

	}
	
	/**
	 * @return
	 */
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 *
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
