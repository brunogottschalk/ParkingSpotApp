package com.parkingSpot.app.authentication.configurations;

import com.parkingSpot.app.repositories.UserRepository;
import com.parkingSpot.app.authentication.Customizations.CorsFilter;
import com.parkingSpot.app.authentication.Customizations.CustomAuthenticationProvider;
import com.parkingSpot.app.authentication.Customizations.CustomUserDetailsService;
import com.parkingSpot.app.authentication.Customizations.CustomUsernamePasswordAuthenticationFilter;
import com.parkingSpot.app.authentication.Customizations.JwtTokenVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.key}")
    private String key;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("https://parking-spot-app-frontend.herokuapp.com/login"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authenticationProvider(new CustomAuthenticationProvider());

        http.addFilterAt(new CustomUsernamePasswordAuthenticationFilter(authenticationManager(), key),
                UsernamePasswordAuthenticationFilter.class);

        http.addFilterAfter(new JwtTokenVerifier(key), CustomUsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CorsFilter(), CustomUsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        http.exceptionHandling().authenticationEntryPoint(((request, response, authException) -> {
            response.addHeader("Http-Error", authException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        })).and();

        http.exceptionHandling().accessDeniedHandler(((request, response, accessDeniedException) -> {
            response.addHeader("Http-Error", accessDeniedException.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        })).and();

        http.authorizeRequests()
                .mvcMatchers("/api/admin/**").hasRole("ADMIN")
                .mvcMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.POST, "/signup").permitAll()
                .mvcMatchers("/login").permitAll()
                .anyRequest().denyAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
