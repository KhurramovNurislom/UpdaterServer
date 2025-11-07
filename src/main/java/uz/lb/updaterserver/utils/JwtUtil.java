package uz.lb.updaterserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.lb.updaterserver.dto.JwtDTO;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static String SECRET_KEY;

    @Value("${jwt.secret.key}")
    public void setSecretKey(String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    private static final int TOKEN_LIVE_TIME = 1000 * 3600 * 24;

    public static String encode(String login, String role) {

        System.out.println("JwtUtil.encode.login -> " + login);
        System.out.println("JwtUtil.encode.role -> " + role);


        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("role", role);

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(login)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_LIVE_TIME))
                .signWith(getSignInKey())
                .compact();
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String login = claims.getSubject();
        String role = (String) claims.get("role");


        return new JwtDTO(login, role);
    }

    private static SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


    public boolean isTokenValid(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Date expDate = claims.getExpiration();
        return expDate.before(new Date());
    }

}
