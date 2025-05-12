package e_store.services;

import e_store.dto.in.AddressCreateUpdateDto;
import e_store.dto.out.AddressReadDto;
import e_store.mappers.in.AddressCreateUpdateMapper;
import e_store.mappers.out.AddressReadMapper;
import e_store.repositories.AddressRepo;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AddressService {

    private final AddressCreateUpdateMapper addressCreateUpdateMapper;
    private final AddressReadMapper addressReadMapper;
    private final AddressRepo addressRepo;

    public AddressService(AddressCreateUpdateMapper addressCreateUpdateMapper,
                          AddressReadMapper addressReadMapper,
                          AddressRepo addressRepo) {
        this.addressCreateUpdateMapper = addressCreateUpdateMapper;
        this.addressReadMapper = addressReadMapper;
        this.addressRepo = addressRepo;
    }

    @Transactional(readOnly = true)
    public List<AddressReadDto> findAll() {
        return addressRepo
                .findAll()
                .stream()
                .map(addressReadMapper::map)
                .toList();
    }

    @Transactional(readOnly = true)
    public AddressReadDto findById(Long id) {
        var entity = addressRepo.findById(id).orElseThrow(() -> new ValidationException("not found, TEXT!1!!"));
        return addressReadMapper.map(entity);
    }

    public AddressReadDto create(AddressCreateUpdateDto dto) {
        var entity = addressCreateUpdateMapper.map(dto);
        var savedEntity = addressRepo.save(entity);
        return addressReadMapper.map(savedEntity);
    }

    public AddressReadDto update(Long id, AddressCreateUpdateDto updateDto) {
        var entity = addressRepo.findById(id).orElseThrow(() -> new ValidationException("not found, TEXT!1!!"));
        var updatedEntity = addressCreateUpdateMapper.mapUpd(updateDto, entity);
        var savedEntity = addressRepo.saveAndFlush(updatedEntity);
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

    public void deleteAll() {
        addressRepo.deleteAll();
    }
}
