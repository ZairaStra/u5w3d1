package zairastra.u5w3d1.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("We haven't found an element with id " + id);
    }
}
