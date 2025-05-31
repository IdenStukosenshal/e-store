package e_store.dto.out;

import java.util.Map;

public record ErrorMessageDto(String message, Map<String, String> fieldErrors) {
}
