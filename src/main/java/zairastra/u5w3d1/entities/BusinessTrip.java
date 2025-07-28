package zairastra.u5w3d1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import zairastra.u5w3d1.entities.enums.BusinessTripStatus;

import java.time.LocalDate;

@Entity
@Table(name = "business_trips")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BusinessTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_trip_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotEmpty(message = "The trip must have a destination")
    private String destination;
    @NotNull(message = "Travel date is required")
    private LocalDate travelDate;
    @Enumerated(EnumType.STRING)
    private BusinessTripStatus businessTripStatus;

    public BusinessTrip(String destination, LocalDate travelDate, BusinessTripStatus businessTripStatus) {
        this.destination = destination;
        this.travelDate = travelDate;
        this.businessTripStatus = businessTripStatus;
    }
}
