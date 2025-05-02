package e_store.dto.out;

import java.util.List;

public record UserReadDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        List<OrderReadDto> ordersDtoLst
) {
}
