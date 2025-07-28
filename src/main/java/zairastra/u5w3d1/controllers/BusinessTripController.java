package zairastra.u5w3d1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zairastra.u5w3d1.entities.BusinessTrip;
import zairastra.u5w3d1.exceptions.ValidationException;
import zairastra.u5w3d1.payloads.NewBusinessTripDTO;
import zairastra.u5w3d1.payloads.NewBusinessTripResponseDTO;
import zairastra.u5w3d1.services.BusinessTripsService;

import java.util.List;

@RestController
@RequestMapping("/businesstrips")
public class BusinessTripController {
    @Autowired
    private BusinessTripsService businessTripsService;

    //SAVE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewBusinessTripResponseDTO createBusinessTrip(@RequestBody @Validated NewBusinessTripDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        BusinessTrip newBusinessTrip = businessTripsService.saveBusinessTrip(payload);
        return new NewBusinessTripResponseDTO(newBusinessTrip.getId());
    }


    //FINDALL
    @GetMapping
    public Page<BusinessTrip> getBusinessTrip(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size) {
        return businessTripsService.findAll(page, size);
    }

    //FINDBYID
    @GetMapping("/{businessTripId}")
    public BusinessTrip getBusinessTripById(@PathVariable long businessTripId) {
        return businessTripsService.findBusinessTripById(businessTripId);
    }

    //FINDBYIDANDUPDATE
    @PutMapping("/{businessTripId}")
    public BusinessTrip getBusinessTripByIdAndUpdate(@PathVariable Long businessTripId, @RequestBody @Validated NewBusinessTripDTO payload) {
        return businessTripsService.findBusinessTripByIdAndUpdate(businessTripId, payload);
    }

    //FINDBYIDANDDELETE
    @DeleteMapping("/{businessTripId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getBusinessTripByIdAndDelete(@PathVariable Long businessTripId) {
        businessTripsService.findBusinessTripByIdAndDelete(businessTripId);
    }
}
