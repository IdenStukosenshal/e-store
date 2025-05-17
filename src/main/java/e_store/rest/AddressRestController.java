package e_store.rest;

import e_store.dto.in.AddressCreateDto;
import e_store.dto.out.AddressReadDto;
import e_store.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userid}/address")
public class AddressRestController {

    private final AddressService addressService;

    public AddressRestController(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping()
    public List<AddressReadDto> findAllByUserId(@PathVariable("userid") Long userId) {
        return addressService.findAllByUserId(userId);
    }

    @GetMapping("/{id}")
    public AddressReadDto findById(@PathVariable("id") Long id) { //@PathVariable("userid") Long userId
        return addressService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //TODO ???
    public AddressReadDto create(@PathVariable("userid") Long userId, @Valid @RequestBody AddressCreateDto dto) {
        return addressService.create(dto, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) { //@PathVariable("userid") Long userId
        addressService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll(@PathVariable("userid") Long userId) {
        addressService.deleteAllByUserId(userId);
    }

}
