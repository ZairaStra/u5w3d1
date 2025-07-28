package zairastra.u5w3d1.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    //lista di messaggi di errori di validazione da aggiungere a super
    private List<String> errorMessages;

    //questi errorMessages serviranno come terzo parametro nel caso si usi una exceptionwithlist
    public ValidationException(List<String> errorMessages) {
        super("Validation errors - ");
        this.errorMessages = errorMessages;
    }
}
