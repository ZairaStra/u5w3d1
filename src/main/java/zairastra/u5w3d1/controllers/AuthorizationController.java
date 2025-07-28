package zairastra.u5w3d1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zairastra.u5w3d1.entities.Employee;
import zairastra.u5w3d1.exceptions.ValidationException;
import zairastra.u5w3d1.payloads.NewEmployeeDTO;
import zairastra.u5w3d1.payloads.NewEmployeeResponseDTO;
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

    //PASSWORD
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEmployeeResponseDTO save(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Employee newEmployee = employeesService.saveEmployee(payload);
            return new NewEmployeeResponseDTO(newEmployee.getId());
        }
    }


}

