package com.kh.carlpion.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kh.carlpion.security.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor

public class SecurityConfigure {
	
	private final JwtFilter filter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity.formLogin(AbstractHttpConfigurer::disable)
						   .httpBasic(AbstractHttpConfigurer::disable)
						   .csrf(AbstractHttpConfigurer::disable)
						   .cors(Customizer.withDefaults())
						   .authorizeHttpRequests(request -> {
							   request.requestMatchers("/admin/**").hasRole("ADMIN");
							   
							   request.requestMatchers(HttpMethod.GET, 
									   "/notice", 
									   "/notice/**", 
									   "/reports", 
									   "/reports/**", 
									   "/reviews", 
									   "/reviews/**", 
									   "/uploads/**", 
									   "/parking/**", 
									   "/rents/**",
									   "/carModel",
									   "/board/**").permitAll();
							   request.requestMatchers(HttpMethod.GET, 
									   "/mypage/**", 
									   "/admin/**", 
									   "/users/getUserInfo",
									   "/mypage/reservation",
									   "/rents/payment/**",
									   "/rents/history/**",
									   "/rents/reservation",
									   "/rents/reservations",
									   "/rents/reservation/**").authenticated();
							   
							   request.requestMatchers(HttpMethod.POST, 
									   "/users", 
									   "/auth/**").permitAll();
							   request.requestMatchers(HttpMethod.POST, 
									   "/rents/**",  
									   "/notice/**", 
									   "/reviews", 
									   "/reports", 
									   "/reviews/**", 
									   "/admin/**").authenticated();
							   request.requestMatchers(HttpMethod.POST, 
									   "/notice", 
									   "/reports", 
									   "/notice/**", 
									   "/admin", 
									   "/reports/**", 
									   "/parking/setting").hasRole("ADMIN");
							   
							   request.requestMatchers(HttpMethod.PUT, 
									   "/users/**", 
									   "/admin/**", 
									   "/reports/**", 
									   "/reviews/**", 
									   "/users/update-pw", 
									   "/users/update-nickname", 
									   "/users/update-email", 
									   "/users/update-profile", 
									   "/users/update-realname").authenticated();
							   request.requestMatchers(HttpMethod.PUT, 
									   "/notice/**", 
									   "/admin/**" ).hasRole("ADMIN");
							   
							   request.requestMatchers(HttpMethod.DELETE, 
									   "/users", 
									   "/notice/**", 
									   "/reports/**", 
									   "/reviews/**",
									   "/rents/reservation/**").authenticated();
							   request.requestMatchers(HttpMethod.DELETE, 
									   "/notice/**", 
									   "/reports/comments", 
									   "/admin/**").hasRole("ADMIN");
							   
						   })
						   .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
						   .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
						   .build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
}
