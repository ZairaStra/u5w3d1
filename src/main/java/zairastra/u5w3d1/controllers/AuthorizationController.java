package zairastra.u5w3d1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zairastra.u5w3d1.payloads.NewLoginDTO;
import zairastra.u5w3d1.payloads.NewLoginResponseDTO;
import zairastra.u5w3d1.services.AuthorizationsService;
import zairastra.u5w3d1.services.EmployeesService;


//mi serve per verificare che l'impiegato sia registrato - per farlo loggare
@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    public EmployeesService employeesService;

    @Autowired
    public AuthorizationsService authorizationsService;

    //LOGIN
    @PostMapping("/login")
    public NewLoginResponseDTO login(@RequestBody NewLoginDTO payload) {
        String extractedToken = authorizationsService.checkEmailBeforeLogin(payload);
        return new NewLoginResponseDTO(extractedToken);
    }

    //in questo caso non ha senso avere un enpoint di registrazione pubblico perchè è un didtema aziendale
    //non si possono registrare utenti a caso, vengono creati da un amministratore
    //meglio lasciarlo filtrato
//    //PASSWORD
//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.CREATED)
//    public NewEmployeeResponseDTO save(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResult) {
//        if (validationResult.hasErrors()) {
//            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
//        } else {
//            Employee newEmployee = employeesService.saveEmployee(payload);
//            return new NewEmployeeResponseDTO(newEmployee.getId());
//        }
//    }


}

