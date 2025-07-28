package zairastra.u5w3d1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotNull(message = "Request date is required")
    private LocalDate requestDate;

    private String optionalPreference;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "business_trip_id")
    private BusinessTrip businessTrip;

    public Reservation(LocalDate requestDate, String optionalPreference, Employee employee, BusinessTrip businessTrip) {
        this.requestDate = requestDate;
        this.optionalPreference = optionalPreference;
        this.employee = employee;
        this.businessTrip = businessTrip;
    }
}
