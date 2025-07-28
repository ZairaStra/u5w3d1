package zairastra.u5w3d1.payloads;

import java.time.LocalDateTime;
import java.util.List;

//mi da gli errori in italiano?????
public record ErrorsWithListDTO(String message, LocalDateTime stamp, List<String> errorsList) {
}
