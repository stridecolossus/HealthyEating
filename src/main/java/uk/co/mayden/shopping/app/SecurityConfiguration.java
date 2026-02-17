package uk.co.mayden.shopping.app;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class SecurityConfiguration {
	@Bean
	SecurityFilterChain filter(HttpSecurity security) {
		return security
			.authorizeHttpRequests(authenticate -> authenticate
					.requestMatchers("/", "/login", "/css/**")
					.permitAll()
					.anyRequest()
					.authenticated()
			)
			.formLogin(form -> form.loginPage("/login").permitAll())
			.logout(LogoutConfigurer::permitAll)
			.build();
	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService(PasswordEncoder encoder) {
		// TODO - mocked / hard-coded account
		final String password = encoder.encode("{noop}password");
		final UserDetails user = User
				.withUsername("shopper")
				.password(password)
				.roles("USER")
				.build();

		// TODO - persistent accounts store
		return new InMemoryUserDetailsManager(user);
	}
}
