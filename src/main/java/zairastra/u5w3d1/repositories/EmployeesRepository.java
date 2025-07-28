package zairastra.u5w3d1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zairastra.u5w3d1.entities.Employee;

import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, Long> {
    //per verificare di non avere nel sistema la stessa mail
    Optional<Employee> findByEmailIgnoreCase(String email);

    //per verificare di non avere nel sistema lo stesso username
    Optional<Employee> findByUsernameIgnoreCase(String username);
}
