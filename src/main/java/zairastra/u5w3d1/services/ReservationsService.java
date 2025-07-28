package zairastra.u5w3d1.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zairastra.u5w3d1.entities.BusinessTrip;
import zairastra.u5w3d1.entities.Employee;
import zairastra.u5w3d1.entities.Reservation;
import zairastra.u5w3d1.exceptions.BadRequestException;
import zairastra.u5w3d1.exceptions.NotFoundException;
import zairastra.u5w3d1.payloads.NewReservationDTO;
import zairastra.u5w3d1.repositories.ReservationsRepository;

@Service
@Slf4j
public class ReservationsService {
    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private BusinessTripsService businessTripsService;

//PRIMA SETTO IL CRUD

    //SAVE
    public Reservation saveReservation(NewReservationDTO payload) {
        Employee employee = employeesService.findEmployeeById(payload.employeeId());
        BusinessTrip businessTrip = businessTripsService.findBusinessTripById(payload.businessTripId());

        // Controllo prenotazioni duplicate
        reservationsRepository.findByEmployeeIdAndTravelDate(employee.getId(), businessTrip.getTravelDate()).ifPresent(reservation -> {
            throw new BadRequestException("Employee " + employee.getName() + " " + employee.getSurname() + " already has a reservation on " + businessTrip.getTravelDate());
        });

        Reservation newReservation = new Reservation(payload.requestDate(), payload.optionalPreference(), employee, businessTrip);
        Reservation savedReservation = reservationsRepository.save(newReservation);
        log.info("A reservation has been created for the employee " + employee.getName() + " " + employee.getSurname() + " on " + businessTrip.getTravelDate());

        return savedReservation;
    }

    //FINDALL
    public Page<Reservation> findAll(int pageNumb, int pageSize) {
        if (pageSize > 30) pageSize = 30;
        Pageable pageable = PageRequest.of(pageNumb, pageSize);
        return reservationsRepository.findAll(pageable);
    }

    //FINDBYID
    public Reservation findReservationById(Long reservationId) {
        return reservationsRepository.findById(reservationId).orElseThrow(() -> new NotFoundException(reservationId));
    }

    //FINDBYIDANDUPDATE - non funziona se non cambio la data???
    public Reservation findReservationByIdAndUpdate(Long reservationId, NewReservationDTO payload) {
        Reservation found = findReservationById(reservationId);

        Employee employee = employeesService.findEmployeeById(payload.employeeId());
        BusinessTrip businessTrip = businessTripsService.findBusinessTripById(payload.businessTripId());

        // Controllo prenotazioni duplicate
        reservationsRepository.findByEmployeeIdAndTravelDate(employee.getId(), businessTrip.getTravelDate()).ifPresent(reservation -> {
            throw new BadRequestException("Employee " + employee.getName() + " " + employee.getSurname() + " already has a reservation on " + businessTrip.getTravelDate());
        });

        found.setRequestDate(payload.requestDate());
        found.setOptionalPreference(payload.optionalPreference());
        found.setEmployee(employee);
        found.setBusinessTrip(businessTrip);

        Reservation updatedReservation = reservationsRepository.save(found);

        log.info("Reservation with ID " + updatedReservation.getId() + " has been updated");

        return updatedReservation;
    }


    //FINDBYIDANDDELETE
    public void findReservationByIdAndDelete(Long reservationId) {
        Reservation found = findReservationById(reservationId);
        reservationsRepository.delete(found);
    }
}
