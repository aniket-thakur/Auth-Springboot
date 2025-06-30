package com.learnauth.auth_service.filters;

import com.learnauth.auth_service.services.JwtService;
import com.learnauth.auth_service.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserDetailsServiceImpl userDetailsService;

    private JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService){
        this.jwtService =jwtService;
        this.userDetailsService = userDetailsService;
    }

//     Excluded endpoints from filter chain
    @Override
    protected  boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getServletPath();
        return path.equals("/api/v1/auth/signup/passenger") ||
                path.equals("/api/v1/auth/signin/passenger");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        String email = null;
        if(request.getCookies() != null){
            for(Cookie cookie :  request.getCookies()){
                if(cookie.getName().equals("jwtToken")){
                    token =cookie.getValue();
                }
            }
        }

        if(token == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        System.out.println("Filter incoming req");
        try{
            email = jwtService.getUsername(token);  // for this username is the email
        }
        catch(Exception e){
            System.out.println("Error extracting username(email) from token : "+ e.getMessage());
        }

        if(email != null){
            System.out.println("Email" + email);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if(jwtService.validateToken(token,userDetails.getUsername())){
                System.out.println("UserDetails : " + userDetails);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,null);

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("Security context: "+ authenticationToken);

            }
        }
        System.out.println("Calling do filter");
        filterChain.doFilter(request,response);


    }

}
