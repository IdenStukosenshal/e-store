package e_store.services;

import e_store.database.entity.Address;
import e_store.database.entity.User;
import e_store.dto.in.AddressCreateDto;
import e_store.dto.out.AddressReadDto;
import e_store.mappers.in.AddressCreateMapper;
import e_store.mappers.out.AddressReadMapper;
import e_store.repositories.AddressRepo;
import e_store.repositories.UserRepo;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AddressService {

    private final AddressCreateMapper addressCreateMapper;
    private final AddressReadMapper addressReadMapper;
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    public AddressService(AddressCreateMapper addressCreateMapper,
                          AddressReadMapper addressReadMapper,
                          AddressRepo addressRepo,
                          UserRepo userRepo) {
        this.addressCreateMapper = addressCreateMapper;
        this.addressReadMapper = addressReadMapper;
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
    }

    @Transactional(readOnly = true)
    public List<AddressReadDto> findAllByUserId(Long id) {
        return addressRepo
                .findAllByUserId(id)
                .stream()
                .map(addressReadMapper::map)
                .toList();
    }

    @Transactional(readOnly = true)
    public AddressReadDto findById(Long id) {
        Address entity = addressRepo.findById(id).orElseThrow(() -> new ValidationException("not found, TEXT!1!!"));
        return addressReadMapper.map(entity);
    }

    public AddressReadDto create(AddressCreateDto dto, Long userId) {
        Address entity = addressCreateMapper.map(dto);
        entity.setUser(findUser(userId));
        var savedEntity = addressRepo.save(entity);
        return addressReadMapper.map(savedEntity);
    }

    public void deleteById(Long id) {
        if (addressRepo.existsById(id)) {
            addressRepo.deleteById(id);
            addressRepo.flush();
        } else {
            throw new ValidationException("not found, TEXT!1!!");
        }
    }

    public void deleteAllByUserId(Long userId) {
        addressRepo.deleteAllByUserId(userId);
    }

    private User findUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ValidationException("not found, TEXT!1!!"));
    }
}
