package com.anodiam.JWTAuth.security;

import com.anodiam.JWTAuth.db.UserRepository;
import com.anodiam.JWTAuth.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtProperties jwtProperties;
    private UserRepository userRepository;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserRepository userRepository,
                                  JwtProperties jwtProperties) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

//        Read the Authorization header where the JWT Token should be
        String header = request.getHeader(jwtProperties.getHEADER_STRING());
//        If header does not contain BEARER or is null, delegate to Spring Impl and exit
        if(header==null || !header.startsWith(jwtProperties.getTOKEN_PREFIX())) {
            chain.doFilter(request, response);
            return;
        }
//        If proper Header is present, try to grab User Principal from database and perform Authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {

        String token = request.getHeader(jwtProperties.getHEADER_STRING());
        if(token != null) {
//            Parse the token and validate it
            String userName = JWT.require(Algorithm.HMAC512(jwtProperties.getSECRET().getBytes()))
                    .build()
                    .verify(token.replace(jwtProperties.getTOKEN_PREFIX(), ""))
                    .getSubject();
//            Search in the DB if we find the user by Token Subject (username)
//            if so then grab user details and create Spring Auth token using username, pass, authorities / roles
            if(userName != null) {
                User user = userRepository.findByUsername(userName);
                UserPrincipal principal = new UserPrincipal(user);
                UsernamePasswordAuthenticationToken authenticationToken = new
                        UsernamePasswordAuthenticationToken(userName, null, principal.getAuthorities());
                return authenticationToken;
            }
            return null;
        }
        return null;
    }
}
