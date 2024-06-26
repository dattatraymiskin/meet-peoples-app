package com.meetpeoples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
//		 httpSecurity.csrf().disable()
//         .authorizeRequests().antMatchers("/authenticate").permitAll()
//         .anyRequest().authenticated()
//         .and().sessionManagement()
//         .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//            httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//		
            httpSecurity
				.sessionManagement(sessionMgmt -> sessionMgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers("/v1/**").authenticated().anyRequest().permitAll())
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.csrf(csrf -> csrf.disable());
		return httpSecurity.build();
	}
}
