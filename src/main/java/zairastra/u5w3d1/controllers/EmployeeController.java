package zairastra.u5w3d1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zairastra.u5w3d1.entities.Employee;
import zairastra.u5w3d1.exceptions.ValidationException;
import zairastra.u5w3d1.payloads.NewEmployeeDTO;
import zairastra.u5w3d1.payloads.NewEmployeeResponseDTO;
import zairastra.u5w3d1.services.EmployeesService;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeesService employeesService;

    //tutte l epath pubbliche saranno accessibili solo agli admin

    //SAVE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public NewEmployeeResponseDTO createEmployee(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        Employee newEmployee = employeesService.saveEmployee(payload);
        return new NewEmployeeResponseDTO(newEmployee.getId());
    }


    //FINDALL
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Employee> getEmployees(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size) {
        return employeesService.findAll(page, size);
    }

    //FINDBYID
    @GetMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee getEmployeeById(@PathVariable long employeeId) {
        return employeesService.findEmployeeById(employeeId);
    }

    //FINDBYIDANDUPDATE
    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee getEmployeeByIdAndUpdate(@PathVariable Long employeeId, @RequestBody @Validated NewEmployeeDTO payload) {
        return employeesService.findEmployeeByIdAndUpdate(employeeId, payload);
    }

    //FINDBYIDANDDELETE
    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void getEmployeeByIdAndDelete(@PathVariable Long employeeId) {
        employeesService.findEmployeeByIdAndDelete(employeeId);
    }

    //le path/me saranno accessibili solo all'utente con la relativa modifica e cancellazione dell'account
    // (metterei qui anche la patch di modifica dell'avatar che senso ha permettere di cambiarlo all'amministratore???)
    @GetMapping("/me")
    public Employee getOwnProfile(@AuthenticationPrincipal Employee authorizedEmployee) {
        return authorizedEmployee;
    }

    @PutMapping("/me")
    public Employee updateOwnProfile(@AuthenticationPrincipal Employee authorizedEmployee, @RequestBody @Validated NewEmployeeDTO payload) {
        return this.employeesService.findEmployeeByIdAndUpdate(authorizedEmployee.getId(), payload);
    }

    @DeleteMapping("/me")
    public void deleteOwnProfile(@AuthenticationPrincipal Employee authorizedEmployee) {
        this.employeesService.findEmployeeByIdAndDelete(authorizedEmployee.getId());
    }
}
