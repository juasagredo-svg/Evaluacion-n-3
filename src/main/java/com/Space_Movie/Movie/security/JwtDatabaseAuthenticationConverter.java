
package com.Space_Movie.Movie.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtDatabaseAuthenticationConverter
        implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserDetailsService userDetailsService;

    public JwtDatabaseAuthenticationConverter(
            UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        UserDetails usuario =
                userDetailsService.loadUserByUsername(jwt.getSubject());

        return new UsernamePasswordAuthenticationToken(
                usuario,
                jwt,
                usuario.getAuthorities()
        );
    }
}
