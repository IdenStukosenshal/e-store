package e_store.rest;

import e_store.dto.in.AddressCreateUpdateDto;
import e_store.dto.out.AddressReadDto;
import e_store.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressRestController {

    private final AddressService addressService;

    public AddressRestController(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping()
    public List<AddressReadDto> findAllByUserId(@RequestParam Long userId) {
        return addressService.findAllByUserId(userId);
    }

    @GetMapping("/{id}")
    public AddressReadDto findById(@PathVariable("id") Long id) {
        return addressService.findById(id); //orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //TODO ???
    public AddressReadDto create(@Valid @RequestBody AddressCreateUpdateDto dto) {
        return addressService.create(dto);
    }

    @PutMapping("/{id}")
    public AddressReadDto update(@PathVariable("id") Long id, @Valid @RequestBody AddressCreateUpdateDto updateDto) {
        return addressService.update(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        addressService.deleteById(id);
    }

    /*
    @DeleteMapping
    public void deleteAll() {addressService.deleteAll();}
     */

}
