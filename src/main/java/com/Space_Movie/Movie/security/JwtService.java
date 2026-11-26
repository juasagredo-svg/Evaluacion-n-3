
package com.Space_Movie.Movie.security;

import java.time.Instant;
import java.util.List;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Value("${app.jwt.expiration}")
    private long expiration;

    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generarToken(UserDetails userDetails) {

        Instant ahora = Instant.now();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("space-movie")
                .issuedAt(ahora)
                .expiresAt(ahora.plusMillis(expiration))
                .subject(userDetails.getUsername())
                .claim("roles", roles)
                .build();
        
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }

    public String extraerCorreo(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    public boolean esTokenValido(String token, UserDetails userDetails) {
        try {
            Jwt jwt = jwtDecoder.decode(token);

            return jwt.getSubject().equals(userDetails.getUsername())
                    && jwt.getExpiresAt() != null
                    && jwt.getExpiresAt().isAfter(Instant.now());

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
