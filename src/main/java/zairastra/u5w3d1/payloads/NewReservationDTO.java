package zairastra.u5w3d1.payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewReservationDTO(
        @NotNull(message = "Request date is required")
        LocalDate requestDate,
        String optionalPreference,
        @NotNull(message = "Employee ID is required")
        Long employeeId,
        @NotNull(message = "BusinessTrip ID is required")
        Long businessTripId) {
}
