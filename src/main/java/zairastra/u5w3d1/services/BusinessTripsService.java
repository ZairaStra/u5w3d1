package zairastra.u5w3d1.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zairastra.u5w3d1.entities.BusinessTrip;
import zairastra.u5w3d1.entities.enums.BusinessTripStatus;
import zairastra.u5w3d1.exceptions.BadRequestException;
import zairastra.u5w3d1.exceptions.NotFoundException;
import zairastra.u5w3d1.payloads.NewBusinessTripDTO;
import zairastra.u5w3d1.repositories.BusinessTripsRepository;

import java.time.LocalDate;

@Service
@Slf4j
public class BusinessTripsService {
    @Autowired
    private BusinessTripsRepository businessTripsRepository;

    //PRIMA SETTO IL CRUD

    //SAVE
    public BusinessTrip saveBusinessTrip(NewBusinessTripDTO payload) {
        businessTripsRepository.findByTravelDateAndDestinationIgnoreCase(payload.travelDate(), payload.destination()).ifPresent(businessTrip -> {
            throw new BadRequestException("A business trip to " + payload.destination() + " on " + payload.travelDate() + " is already scheduled");
        });

        //devo trasformare la stringa in enum -> VERIFICARE CHE PASSI LA STRINGA CORRETTA
        //trasformala in maiuscola
        BusinessTripStatus status;
        try {
            status = BusinessTripStatus.valueOf(payload.businessTripStatus().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid business trip status: " + payload.businessTripStatus() + "; insert SCHEDULED or COMPLETED");
        }
        //devo fare un controllo sull'aggiungere appuntamenti nel passato come scheduled
        if (status == BusinessTripStatus.SCHEDULED && payload.travelDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot schedule a business trip in the past");
        }
        //e uno per impedire di aggiungere appuntamenti per il futuro completed
        if (status == BusinessTripStatus.COMPLETED && payload.travelDate().isAfter(LocalDate.now())) {
            throw new BadRequestException("It's not possible to add a completed business trip in the future");
        }

        BusinessTrip newBusinessTrip = new BusinessTrip(payload.destination(), payload.travelDate(), status);
        BusinessTrip savedBusinessTrip = businessTripsRepository.save(newBusinessTrip);

        log.info("A trip to " + payload.destination() + " on" + payload.travelDate() + " has been scheduled");

        return savedBusinessTrip;
    }

    //FINDALL
    public Page<BusinessTrip> findAll(int pageNumb, int pageSize) {
        if (pageSize > 30) pageSize = 30;
        Pageable pageable = PageRequest.of(pageNumb, pageSize);
        return businessTripsRepository.findAll(pageable);
    }

    //FINDBYID
    public BusinessTrip findBusinessTripById(Long businessTripId) {
        return businessTripsRepository.findById(businessTripId).orElseThrow(() -> new NotFoundException(businessTripId));
    }


    //FINDBYIDANDUPDATE
    public BusinessTrip findBusinessTripByIdAndUpdate(Long tripId, NewBusinessTripDTO payload) {
        BusinessTrip found = findBusinessTripById(tripId);

        //devo trasformare la stringa in enum -> VERIFICARE CHE PASSI LA STRINGA CORRETTA
        //trasformala in maiuscola
        BusinessTripStatus status;
        try {
            status = BusinessTripStatus.valueOf(payload.businessTripStatus().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid business trip status: " + payload.businessTripStatus() + "; insert SCHEDULED or COMPLETED");
        }

        found.setDestination(payload.destination());
        found.setTravelDate(payload.travelDate());
        found.setBusinessTripStatus(status);

        BusinessTrip updatedTrip = businessTripsRepository.save(found);

        log.info("Business trip to " + updatedTrip.getDestination() + " on " + updatedTrip.getTravelDate() + " has been updated");

        return updatedTrip;
    }

    //FINDBYIDANDDELETE
    public void findBusinessTripByIdAndDelete(Long businessTripId) {
        BusinessTrip found = findBusinessTripById(businessTripId);
        businessTripsRepository.delete(found);
    }

}
