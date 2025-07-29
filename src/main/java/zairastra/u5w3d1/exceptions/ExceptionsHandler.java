package zairastra.u5w3d1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zairastra.u5w3d1.payloads.ErrorDTO;
import zairastra.u5w3d1.payloads.ErrorsWithListDTO;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    //BAD REQUEST - 400
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(BadRequestException exception) {
        return new ErrorDTO(exception.getMessage(), LocalDateTime.now());
    }

    //BAD REQUEST - ERRORI DI VALIDAZIONE - 400
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsWithListDTO handleValidationErrors(ValidationException exception) {
        return new ErrorsWithListDTO(exception.getMessage(), LocalDateTime.now(), exception.getErrorMessages());
    }

    //aggiungo l'errore
    //UNAUTHORIZED - 401
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorized(UnauthorizedException exception) {
        return new ErrorDTO(exception.getMessage(), LocalDateTime.now());
    }

    //NOTFOUNDEXCEPTION// - 404
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(NotFoundException exception) {
        return new ErrorDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ErrorDTO handleForbidden(AuthorizationDeniedException ex) {
        return new ErrorDTO("Authorization denied", LocalDateTime.now());
    }

    //ECCEZIONI NON GESTITE SINGOLARMENTE - 500
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleServerError(Exception exception) {
        exception.printStackTrace();//questo mi stampa in console lo stack trace per capire dove sta l'errore
        return new ErrorDTO("Ooops, we have a problem!", LocalDateTime.now());
    }
}
