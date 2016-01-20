package br.com.metronic.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService users;
	
	/**
	 * Responsible for define the URL restrictions
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/signup", "/forget").permitAll()
			.antMatchers("/PRIVACY_POLICY.pdf", "/TERMS_OF_SERVICE.pdf").permitAll()
			.anyRequest().authenticated()
			.antMatchers("/logout").hasAnyRole("USER","ADMIN")
			.antMatchers("/dashboard/**").hasAnyRole("USER","ADMIN")
			.antMatchers("/notification/**").hasAnyRole("USER","ADMIN")
			.and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/dashboard", true).failureUrl("/login?to=error").permitAll()
			    .usernameParameter("username").passwordParameter("password")		
			.and()
			    .csrf();
	}

	/**
	 * Responsible for igonre the static files [css/js/images]
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	
	/**
	 * Responsible for user authentication
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(users).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	/**
	 * Responsible for create an intance of Crypt
	 * @return
	 */
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
}
