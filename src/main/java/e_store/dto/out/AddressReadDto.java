package e_store.dto.out;

public record AddressReadDto(
        Long id,
        String city,
        String postalCode,
        String streetAddress
) {
}
