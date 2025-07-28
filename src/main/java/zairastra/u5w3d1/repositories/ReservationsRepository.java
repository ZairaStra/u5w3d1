package zairastra.u5w3d1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zairastra.u5w3d1.entities.Reservation;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, Long> {
    //per evitare due prenotazioni lo stesso giorno
    //devo passare da Reservation in BusinessTrip (employeeId la ho già)
    @Query("SELECT r FROM Reservation r WHERE r.employee.id= :employeeId AND r.businessTrip.travelDate= :travelDate")
    Optional<Reservation> findByEmployeeIdAndTravelDate(Long employeeId, LocalDate travelDate);
}
