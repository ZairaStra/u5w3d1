package zairastra.u5w3d1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zairastra.u5w3d1.entities.BusinessTrip;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BusinessTripsRepository extends JpaRepository<BusinessTrip, Long> {
    //per verificare di non salvare due volte un viaggio per lo stesso giorno nello stesso posto
    Optional<BusinessTrip> findByTravelDateAndDestinationIgnoreCase(LocalDate travelDate, String destination);
}
