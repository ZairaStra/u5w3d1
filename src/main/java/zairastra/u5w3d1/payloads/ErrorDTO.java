package zairastra.u5w3d1.payloads;

import java.time.LocalDateTime;

public record ErrorDTO(String message, LocalDateTime stamp) {
}
