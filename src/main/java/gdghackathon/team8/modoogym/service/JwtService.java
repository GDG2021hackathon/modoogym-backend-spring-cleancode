package gdghackathon.team8.modoogym.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdghackathon.team8.modoogym.domain.user.UserRepository;
import gdghackathon.team8.modoogym.dto.login.PayloadDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    private final UserRepository userRepository;

    private static final long EXP_TIME = 1000 * 60 * 60; // 1시간

    public String createToken(PayloadDto payloadDto) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        Map payload = convertObjectToMap(payloadDto);

        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(payload)
            .signWith(signingKey, signatureAlgorithm)
            .setExpiration(new Date(System.currentTimeMillis() + EXP_TIME))
            .compact();
    }

    private Map convertObjectToMap(PayloadDto payloadDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(payloadDto, Map.class);
    }

    public PayloadDto getPayload(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
            .build()
            .parseClaimsJws(token)
            .getBody();

        return PayloadDto.builder()
            .email(claims.get("email", String.class))
            .userId(claims.get("userId", Long.class))
            .build();
    }

    public boolean isValidToken(String token) {
        try {
            PayloadDto payload = getPayload(token);
            String requestUserEmail = payload.getEmail();
            Long requestUserId = payload.getUserId();

            String findUserEmail = userRepository.findById(requestUserId)
                .orElseThrow(() -> new JwtException("Token Error"))
                .getEmail();
            if (findUserEmail.equals(requestUserEmail)) {
                return true;
            }
        } catch (ExpiredJwtException e) {
            log.error("Token Expired: {}", e.getMessage());
        } catch (JwtException e) {
            log.error("Token Error: {}", e.getMessage());
        }
        return false;
    }
}
