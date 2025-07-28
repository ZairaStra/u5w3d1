package zairastra.u5w3d1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zairastra.u5w3d1.services.EmployeesService;
import zairastra.u5w3d1.tools.JWTTools;

//mi serve per verificare che l'impiegato sia registrato - per farlo loggare
@Service
public class AuthorizationController {
    @Autowired
    public EmployeesService employeesService;

    @Autowired
    public JWTTools jwtTools;

   
}
