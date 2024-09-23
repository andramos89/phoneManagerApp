package com.example.phonecontactapp;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
public class SecurityConfiguration {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorizeHttpRequests ->
		//	authorizeHttpRequests
		//		.requestMatchers("/phone/v1/**", "/swagger-ui/**","/v3/api-docs/**", "/swagger-ui/**", "swagger-resources/**", "swagger-resources").permitAll());
		//no need for authentication. This can be achieved, p.e. with a jwt token via cors filter
		authorizeHttpRequests.anyRequest().permitAll());
		return http.build();
	}

}
