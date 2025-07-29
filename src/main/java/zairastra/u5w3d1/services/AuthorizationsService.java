package zairastra.u5w3d1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zairastra.u5w3d1.entities.Employee;
import zairastra.u5w3d1.exceptions.UnauthorizedException;
import zairastra.u5w3d1.payloads.NewLoginDTO;
import zairastra.u5w3d1.tools.JWTTools;

@Service

public class AuthorizationsService {
    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bCrypt;

    public String checkEmailBeforeLogin(NewLoginDTO payload) {
        Employee found = employeesService.findByEmail(payload.email()); //ottengo l'impiegato dalla sua mail
        if (bCrypt.matches(payload.password(), found.getPassword())) { // verifico la psw - che sia uguale a quella del payolad
            String extractedToken = jwtTools.createToken(found); //raggiungo il token
            return extractedToken;
        } else {
            throw new UnauthorizedException("Unauthorized - try again");
        }
    }
}
