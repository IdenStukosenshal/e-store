package e_store.dto.out;

public record AddressReadDto(
        String city,
        String postalCode,
        String streetAddress
) {
}
