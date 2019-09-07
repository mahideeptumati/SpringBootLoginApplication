package com.springboot.login.security;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;




@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**","/css/**","/templates/**","/webjars/**","/applet/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http
				.authorizeRequests()
				.antMatchers("/registerUser").permitAll()
				.antMatchers("/loginerror").permitAll()
				.anyRequest().authenticated()
				.and().
				formLogin().loginPage("/userLogin").defaultSuccessUrl("/").permitAll().
				and()
				.addFilterBefore(createUserNamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).deleteCookies("JSESSIONID", "XSRF-TOKEN")
				.logoutSuccessHandler(getLogoutSuccessHandler())
				.and()
				.csrf().disable()

				.sessionManagement().maximumSessions(1).expiredUrl("/userLogin").sessionRegistry(getSessionRegistry());



	}

	@Bean
	public LogoutSuccessHandler getLogoutSuccessHandler() {
		UserService logoutSuccessHandler = new UserService();
		logoutSuccessHandler.setDefaultTargetUrl("/userLogin");
		return logoutSuccessHandler;
	}
	@Bean
	public UsernamePasswordAuthenticationFilter createUserNamePasswordAuthenticationFilter() throws Exception {

		UsernamePasswordAuthenticationFilter filter=new LoginFilter();

		filter.getPasswordParameter();
		filter.setAuthenticationManager(authenticationManagerBean());// this is the user bean which has the list of authentication tokens which has the users details if that is authentiucated
		filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
		filter.setAuthenticationSuccessHandler(getauthenticationSuccessHandler());
		filter.setAuthenticationFailureHandler(getauthenticationFailureHandler("/loginerror"));
		filter.setSessionAuthenticationStrategy(getSessionAuthenticationStrategy());
		System.out.println("filter");
		return filter;

	}


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		List<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
		authenticationProviderList.add(createAuthenticationProvider());


		//this list will have all the data about the users...if they are present
		AuthenticationManager authenticationManager = new ProviderManager(authenticationProviderList);
		return authenticationManager;
	}

	@Bean
	public AuthenticationSuccessHandler getauthenticationSuccessHandler() {
		return new SuccessHandler();
	}
	@Bean
	public AuthenticationFailureHandler getauthenticationFailureHandler(String url) {
		return new FailureHandler(url);
	}


	@Bean
	public AuthenticationProvider createAuthenticationProvider() {
		return new AuthenticationProviderClass(); // returns if the user exdists or not
	}


	@Bean
	public SessionAuthenticationStrategy getSessionAuthenticationStrategy() {
		List<SessionAuthenticationStrategy> sessionAuthenticationStrategyList = new LinkedList<>();

		sessionAuthenticationStrategyList.add(getConcurrentSessionControlAuthenticationStrategy());
		sessionAuthenticationStrategyList.add(getSessionFixationProtectionStrategy());
		sessionAuthenticationStrategyList.add(getRegisterSessionAuthenticationStrategy());

		CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy= new CompositeSessionAuthenticationStrategy(sessionAuthenticationStrategyList);

		return compositeSessionAuthenticationStrategy;
	}

	@Bean
	public ConcurrentSessionControlAuthenticationStrategy getConcurrentSessionControlAuthenticationStrategy() {
		ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(getSessionRegistry());
		concurrentSessionControlAuthenticationStrategy.setMaximumSessions(1);
		return concurrentSessionControlAuthenticationStrategy;
	}

	@Bean
	public SessionRegistry getSessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public SessionFixationProtectionStrategy getSessionFixationProtectionStrategy() {
		SessionFixationProtectionStrategy sessionFixationProtectionStrategy = new SessionFixationProtectionStrategy();
		return sessionFixationProtectionStrategy;
	}

	@Bean
	public RegisterSessionAuthenticationStrategy getRegisterSessionAuthenticationStrategy() {
		RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy = new RegisterSessionAuthenticationStrategy(getSessionRegistry());
		return registerSessionAuthenticationStrategy;
	}


}
