package pe.edu.upn.rest.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioDetailsService usuarioDetailsService;
	
	@Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/index.html").permitAll()
				.antMatchers("/plato/nuevo").hasRole("GERENTE")
				.antMatchers("/plato/edit/**").hasRole("GERENTE")
				.antMatchers("/plato/del/**").hasRole("GERENTE")
				.antMatchers("/tipo").hasRole("GERENTE")
				.antMatchers("/tipo/nuevo").hasRole("GERENTE")
				.antMatchers("/tipo/info/**").hasRole("GERENTE")
				.antMatchers("/tipo/edit/**").hasRole("GERENTE")
				.antMatchers("/tipo/del/**").hasRole("GERENTE")
				.antMatchers("/personal").hasRole("GERENTE")
				.antMatchers("/personal/nuevo").hasRole("GERENTE")
				.antMatchers("/personal/info/**").hasRole("GERENTE")
				.antMatchers("/personal/edit/**").hasRole("GERENTE")
				.antMatchers("/personal/del/**").hasRole("GERENTE")
				.antMatchers("/pedido").authenticated() //.hasRole("GERENTE")
				.antMatchers("/pedido/nuevo").authenticated()
				.antMatchers("/pedido/info/**").hasRole("GERENTE")
				.antMatchers("/pedido/edit/**").authenticated()
				.antMatchers("/pedido/del/**").hasRole("GERENTE")
				.antMatchers("/usuario").hasRole("GERENTE")
				.antMatchers("/usuario/camarero").hasRole("GERENTE")
			.and()
			.formLogin()
				.loginProcessingUrl("/signin")
				.loginPage("/login").permitAll()
				.usernameParameter("inputUsername")
                .passwordParameter("inputPassword")
			.and()
	        .logout()
	        	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	        	.logoutSuccessUrl("/")
	        .and()
            .rememberMe()
            	.tokenValiditySeconds(2592000)
            	.key("Cl4v3.")
            	.rememberMeParameter("checkRememberMe")
            	.userDetailsService(usuarioDetailsService)
            .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder( ) {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.usuarioDetailsService);

        return daoAuthenticationProvider;
    }
	
}
