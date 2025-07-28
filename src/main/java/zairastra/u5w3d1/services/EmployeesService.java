package zairastra.u5w3d1.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zairastra.u5w3d1.entities.Employee;
import zairastra.u5w3d1.exceptions.BadRequestException;
import zairastra.u5w3d1.exceptions.NotFoundException;
import zairastra.u5w3d1.payloads.NewEmployeeDTO;
import zairastra.u5w3d1.repositories.EmployeesRepository;

@Service
@Slf4j
public class EmployeesService {
    @Autowired
    private EmployeesRepository employeesRepository;
//PRIMA SETTO IL CRUD

    //SAVE
    public Employee saveEmployee(NewEmployeeDTO payload) {
        employeesRepository.findByEmailIgnoreCase(payload.email()).ifPresent(employee -> {
            throw new BadRequestException("An employee with email " + payload.email() + " already exists in our system");
        });

        employeesRepository.findByUsernameIgnoreCase(payload.username()).ifPresent(employee -> {
            throw new BadRequestException("An employee with username " + payload.username() + " already exists in our system");
        });

        Employee newEmployee = new Employee(payload.username(), payload.name(), payload.surname(), payload.email(), "https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname());
        Employee savedEmployee = employeesRepository.save(newEmployee);
        log.info("The employee " + payload.name() + " " + payload.surname() + " has been saved");

        return savedEmployee;
    }

    //FINDALL
    public Page<Employee> findAll(int pageNumb, int pageSize) {
        if (pageSize > 30) pageSize = 30;
        Pageable pageable = PageRequest.of(pageNumb, pageSize);
        return employeesRepository.findAll(pageable);
    }

    //FINDBYID
    public Employee findEmployeeById(Long employeeId) {
        return employeesRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    //FINDBYIDANDUPDATE
    public Employee findEmployeeByIdAndUpdate(Long employeeId, NewEmployeeDTO payload) {
        Employee found = findEmployeeById(employeeId);

        if (!found.getEmail().equals(payload.email())) {
            employeesRepository.findByEmailIgnoreCase(payload.email()).ifPresent(employee -> {
                throw new BadRequestException(employee.getEmail() + " already exists in our system");
            });
        }

        if (!found.getUsername().equals(payload.username())) {
            employeesRepository.findByUsernameIgnoreCase(payload.username()).ifPresent(employee -> {
                throw new BadRequestException("Username " + employee.getUsername() + " already exists in our system");
            });
        }

        found.setUsername(payload.username());
        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setEmail(payload.email());

        Employee updatedEmployee = employeesRepository.save(found);

        log.info("The employee " + updatedEmployee.getName() + " " + updatedEmployee.getSurname() + " has been updated");
        return updatedEmployee;
    }

    //FINDBYIDANDDELETE
    public void findEmployeeByIdAndDelete(Long employeeId) {
        Employee found = findEmployeeById(employeeId);
        employeesRepository.delete(found);
    }

    //IMGUPLOAD

}
