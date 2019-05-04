package com.pankaj.spring.cloud.apigateway.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pankaj.spring.cloud.apigateway.component.AuthProvider;
import com.pankaj.spring.cloud.apigateway.filter.AuthFilter;
import com.pankaj.spring.cloud.apigateway.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

/*	@Resource(name = "userDetailsService")
	private UserDetailsService userDetailsService;
*/
	/*@Autowired
	private BCryptPasswordEncoder passwordEncoder;*/
	
	@Autowired
	private AuthFilter authFilter;
	
	@Autowired
	private AuthProvider authProvider;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth)
			throws Exception {
		/*auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder);*/
		auth.authenticationProvider(authProvider);
	}

	/*@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean()
			throws Exception {
		return new JwtAuthenticationFilter();
	}*/
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/**/users/authenticate", "/**/users/signup", "/swagger-ui.html","/webjars/springfox-swagger-ui/**","/swagger-resources/**","/**/v2/api-docs").permitAll()
				.anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authFilter,
				UsernamePasswordAuthenticationFilter.class);
	}
}
