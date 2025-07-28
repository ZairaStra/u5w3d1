package zairastra.u5w3d1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zairastra.u5w3d1.entities.Reservation;
import zairastra.u5w3d1.exceptions.ValidationException;
import zairastra.u5w3d1.payloads.NewReservationDTO;
import zairastra.u5w3d1.payloads.NewReservationResponseDTO;
import zairastra.u5w3d1.services.ReservationsService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationsService reservationsService;

    //SAVE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewReservationResponseDTO createEmployee(@RequestBody @Validated NewReservationDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        Reservation newReservation = reservationsService.saveReservation(payload);
        return new NewReservationResponseDTO(newReservation.getId());
    }


    //FINDALL
    @GetMapping
    public Page<Reservation> getReservations(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size) {
        return reservationsService.findAll(page, size);
    }

    //FINDBYID
    @GetMapping("/{reservationId}")
    public Reservation getReservationById(@PathVariable long reservationId) {
        return reservationsService.findReservationById(reservationId);
    }

    //FINDBYIDANDUPDATE
    @PutMapping("/{reservationId}")
    public Reservation getReservationByIdAndUpdate(@PathVariable Long reservationId, @RequestBody @Validated NewReservationDTO payload) {
        return reservationsService.findReservationByIdAndUpdate(reservationId, payload);
    }

    //FINDBYIDANDDELETE
    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getReservationByIdAndDelete(@PathVariable Long reservationId) {
        reservationsService.findReservationByIdAndDelete(reservationId);
    }
}
