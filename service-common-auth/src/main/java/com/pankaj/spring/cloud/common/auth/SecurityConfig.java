package com.pankaj.spring.cloud.common.auth;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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

	@Override
	public void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		/*auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder);*/
		auth.authenticationProvider(this.authProvider);
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
				//.antMatchers("/**/users/authenticate", "/**/users/signup", "/swagger-ui.html","/webjars/springfox-swagger-ui/**","/swagger-resources/**","/**/v2/api-docs").permitAll()
				.antMatchers("/api/protected/**").authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint())
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
		        .addFilterBefore(this.authFilter,UsernamePasswordAuthenticationFilter.class);
	}
}
