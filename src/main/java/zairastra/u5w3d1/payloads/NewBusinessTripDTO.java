package zairastra.u5w3d1.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewBusinessTripDTO(
        @NotEmpty(message = "The trip must have a destination")
        String destination,
        @NotNull(message = "Travel date is required")
        LocalDate travelDate,
        //cambio l'enum in una stringa - devo capire come convertirla
//        @Enumerated(EnumType.STRING)
//        BusinessTripStatus businessTripStatus
        @NotEmpty(message = "Trip status is required")
        String businessTripStatus) {
}
