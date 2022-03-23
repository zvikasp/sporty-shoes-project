package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import service.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/admin/orderList", "/admin/order", "/admin/accountInfo")//
				.access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN', 'ROLE_CUSTOMER')");
		http.authorizeRequests().antMatchers("/admin/product")//
				.access("hasRole('ROLE_ADMIN')");
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests().and().formLogin()//
				.loginProcessingUrl("/j_spring_security_check")//
				.loginPage("/admin/login")//
				.defaultSuccessUrl("/admin/accountInfo")//
				.failureUrl("/admin/login?error=true")//
				.usernameParameter("userName")//
				.passwordParameter("password")//
				.and().logout().logoutUrl("/admin/logout")//
				.logoutSuccessUrl("/");
	}
}
