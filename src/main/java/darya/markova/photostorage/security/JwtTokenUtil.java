package darya.markova.photostorage.security;

import darya.markova.photostorage.security.domain.CustomUserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenUtil implements Serializable {

    private static final String SECRET = "secret";

    /**
     * Время жизни токена в миллисекундах.
     */
    private static final int EXPIRATION_TIME = 30 * 60 * 1000;

    public String getUsernameFromToken(String token) {
        if (isTokenExpired(token)) {
            throw new ExpiredJwtException(null, getAllClaimsFromToken(token), "Token is expired.");
        }
        return getClaimFromToken(token, Claims::getSubject);
    }

//    public Date getIssuedAtDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getIssuedAt);
//    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

//    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
//        return (lastPasswordReset != null && created.before(lastPasswordReset));
//    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

//    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
//        final Date created = getIssuedAtDateFromToken(token);
//        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
//                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
//    }

//    public String refreshToken(String token) {
//        final Date createdDate = clock.now();
//        final Date expirationDate = calculateExpirationDate(createdDate);
//
//        final Claims claims = getAllClaimsFromToken(token);
//        claims.setIssuedAt(createdDate);
//        claims.setExpiration(expirationDate);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
//    }

    public void validateToken(String token, UserDetails userDetails) {
        CustomUserDetailsImpl user = (CustomUserDetailsImpl) userDetails;
        final String username = getUsernameFromToken(token);
        if (!username.equals(user.getUsername())) {
            throw new JwtException("Invalid token - can't authenticate.");
        }
        if (isTokenExpired(token)) {
            throw new ExpiredJwtException(null, getAllClaimsFromToken(token), "Token is expired.");
        }
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + EXPIRATION_TIME/**999999999999999999l*/);
    }
}
