package e_store.services;


import e_store.dto.in.UserCreateUpdateDto;
import e_store.dto.out.UserReadDto;
import e_store.mappers.in.UserCreateUpdateMapper;
import e_store.mappers.out.UserReadMapper;
import e_store.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

    private final UserReadMapper userReadMapper;
    private final UserCreateUpdateMapper userCreateUpdateMapper;
    private final UserRepo userRepo;

    public UserService(UserReadMapper userReadMapper,
                       UserCreateUpdateMapper userCreateUpdateMapper,
                       UserRepo userRepo) {
        this.userReadMapper = userReadMapper;
        this.userCreateUpdateMapper = userCreateUpdateMapper;
        this.userRepo = userRepo;
    }

    @Transactional(readOnly = true)
    public Page<UserReadDto> findAll(PageRequest pageRequest) {
        return userRepo
                .findAll(pageRequest)
                .map(userReadMapper::map);
    }

    @Transactional(readOnly = true)
    public UserReadDto findById(Long id) {
        var entity = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));
        return userReadMapper.map(entity);
    }

    public UserReadDto create(UserCreateUpdateDto dto) {
        var entity = userCreateUpdateMapper.map(dto);
        var savedEntity = userRepo.save(entity);
        return userReadMapper.map(savedEntity);
    }

    public UserReadDto update(Long id, UserCreateUpdateDto updateDto) {
        var entity = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));
        var updatedEntity = userCreateUpdateMapper.mapUpd(updateDto, entity);
        var savedEntity = userRepo.save(updatedEntity);
        return userReadMapper.map(savedEntity);
    }

    public void deleteById(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            userRepo.flush();
        } else {
            throw new EntityNotFoundException("User");
        }
    }

}
