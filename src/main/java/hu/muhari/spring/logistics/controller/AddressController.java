package hu.muhari.spring.logistics.controller;

import hu.muhari.spring.logistics.dto.AddressDto;
import hu.muhari.spring.logistics.dto.AddressExampleDto;
import hu.muhari.spring.logistics.mapper.AddressMapper;
import hu.muhari.spring.logistics.model.Address;
import hu.muhari.spring.logistics.service.AddressService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    AddressService addressService;
    AddressMapper addressMapper;

    public AddressController(AddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @GetMapping
    public List<AddressDto> getAllAddresses() {
        return addressMapper.addressesToDtos(addressService.getAllAddresses());
    }

    @GetMapping("/{id}")
    public AddressDto getAddressById(@PathVariable long id) {
        return addressMapper.addressToDto(addressService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(value = "/search")
    public ResponseEntity<List<AddressDto>> findByExample(
            @RequestBody AddressExampleDto example,
            @PageableDefault(direction = Sort.Direction.ASC, page = 0, size = Integer.MAX_VALUE, sort = "id")
                    Pageable pageable) {

        Page<Address> result = addressService.findAddressesByExample(example, pageable);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Total-Count",
                Long.toString(result.getTotalElements()));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(addressMapper.addressesToDtos(result.getContent()));
    }

    @Transactional
    @PostMapping
    public AddressDto createAddress(@RequestBody @Valid AddressDto addressDto) {
        if (addressDto.getId() != null && addressDto.getId() != 0L)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return addressMapper.addressToDto(addressService.createAddress(addressMapper.dtoToAddress(addressDto)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable long id) {
        try {
            addressService.deleteAddress(id);
        } catch (BadHttpRequest e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public AddressDto modifyAddress(@RequestBody @Valid AddressDto addressDto, @PathVariable long id) {
        if (addressDto.getId() != null && addressDto.getId() != 0 && addressDto.getId() != id)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        addressDto.setId(id);
        AddressDto modifiedAddress;

        try {
            modifiedAddress = addressMapper.addressToDto(addressService.modifyAddress(addressMapper.dtoToAddress(addressDto)));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return modifiedAddress;
    }

}
