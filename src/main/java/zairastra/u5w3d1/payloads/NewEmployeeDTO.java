package zairastra.u5w3d1.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

//modifico l'impiegato in modo che faccia il login
public record NewEmployeeDTO(
        @NotEmpty(message = "Username is required")
        String username,
        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Surname is required")
        String surname,
        @Email
        @NotEmpty(message = "Email is required")
        String email,
        @NotEmpty(message = "Password is required")
        @Size(min = 6)
        String password) {
}
