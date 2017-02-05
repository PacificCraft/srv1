package uk.me.vucko.stefan.srv1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

@Autowired
Srv1UserDetailsService userDetailsService;

@Autowired
CustomAuthenticationProvider customAuthenticationProvider;

@Autowired
RestAuthenticationEntryPoint restAuthenticationEntryPoint;

@Autowired
AuthenticationSuccessHandler authenticationSuccessHandler;

@Override
protected void configure(HttpSecurity http) throws Exception {
	http
		.csrf().disable()
		.exceptionHandling()
			.authenticationEntryPoint(restAuthenticationEntryPoint)
			.and()
		.authorizeRequests()
			.antMatchers("/api/")
			.permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.successHandler(authenticationSuccessHandler)
			.failureHandler(new SimpleUrlAuthenticationFailureHandler())
			.and()
		.logout();
}

@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
auth.authenticationProvider(customAuthenticationProvider);
}
}

