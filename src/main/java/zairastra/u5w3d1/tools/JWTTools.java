package zairastra.u5w3d1.tools;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zairastra.u5w3d1.entities.Employee;
import zairastra.u5w3d1.exceptions.UnauthorizedException;

import java.util.Date;

//in questa classe Component scrivo i metodi per:
//1. creare token
//2. verificarli a richiesta
@Component
public class JWTTools {
    //uso il valore del "sefreto" memorizzaro in env.prop
    @Value("${jwt.secrer}")
    private String secret;

    //creo token per impiegato
    public String createToken(Employee employee) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Data di scadenza del Token (Expiration Date), anche questa in millisecondi
                .subject(String.valueOf(employee.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    //verifico token all'accesso
    public void verifyToken(String accessToken) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(accessToken);
        } catch (Exception ex) {
            throw new UnauthorizedException("Unauthorized - try again");
        }

    }
}
