package e_store.rest;

import e_store.dto.in.UserCreateUpdateDto;
import e_store.dto.out.PageResponseDto;
import e_store.dto.out.UserReadDto;
import e_store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public PageResponseDto<UserReadDto> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size
    ) {
        Page<UserReadDto> pagedData = userService.findAll(PageRequest.of(page, size));
        return PageResponseDto.of(pagedData);
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

}
