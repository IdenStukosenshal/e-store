package e_store.rest;

import e_store.dto.in.UserCreateUpdateDto;
import e_store.dto.out.UserReadDto;
import e_store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserReadDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public UserReadDto create(@Valid @RequestBody UserCreateUpdateDto dto) {
        return userService.create(dto);
    }

    @PutMapping("/{id}")
    public UserReadDto update(@PathVariable("id") Long id, @Valid @RequestBody UserCreateUpdateDto updateDto) {
        return userService.update(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll() {
        userService.deleteAll();
    }
}
