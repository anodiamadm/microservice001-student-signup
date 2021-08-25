package com.anodiam.JWTAuth.security;

import com.anodiam.JWTAuth.db.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${bCrypt.strength}")
    private String bCryptStrength;

    @Value("${bCrypt.salt}")
    private String bCryptSalt;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration.milliseconds}")
    private String EXPIRATION_TIME;

    @Value("${jwt.token.prefix}")
    private String TOKEN_PREFIX;

    @Value("${jwt.header.string}")
    private String HEADER_STRING;

    private UserPrincipalDetailService userPrincipalDetailService;
    private UserRepository userRepository;
    public SecurityConfiguration(UserPrincipalDetailService userPrincipalDetailService,
                                 UserRepository userRepository) {
        this.userPrincipalDetailService = userPrincipalDetailService;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) { //throws Exception
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                Remove CSRF and State in Session coz in JWT those are not required
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                Add JWT Auth filters in proper order >> 1. JwtAuthenticationFilter >> 2. JwtAuthorizationFilter
                .addFilter(new JwtAuthenticationFilter(authenticationManager(),
                           new JwtProperties(SECRET, Integer.parseInt(EXPIRATION_TIME),
                                                          TOKEN_PREFIX, HEADER_STRING)))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepository,
                           new JwtProperties(SECRET, Integer.parseInt(EXPIRATION_TIME),
                                                          TOKEN_PREFIX, HEADER_STRING)))
                .authorizeRequests()
//                Configure Access Rules
                .antMatchers("/login").permitAll()
                .antMatchers("/api/public/management/*").hasRole("MANAGER")
                .antMatchers("/api/public/admin/*").hasRole("ADMIN");
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(Integer.parseInt(bCryptStrength), new SecureRandom(bCryptSalt.getBytes()));
    }
}
