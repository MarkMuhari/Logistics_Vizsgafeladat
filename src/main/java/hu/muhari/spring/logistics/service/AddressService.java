package hu.muhari.spring.logistics.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import hu.muhari.spring.logistics.dto.AddressExampleDto;
import hu.muhari.spring.logistics.model.Address;
import hu.muhari.spring.logistics.repository.AddressRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javassist.tools.web.BadHttpRequest;

@Service
public class AddressService {

    AddressRepository addressRepository;
    MilestoneService milestoneService;

    public AddressService(AddressRepository addressRepository, MilestoneService milestoneService) {
        this.addressRepository = addressRepository;
        this.milestoneService = milestoneService;
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> findById(long id) {
        return addressRepository.findById(id);
    }

    @Transactional
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Transactional
    public void deleteAddress(long id) throws BadHttpRequest {
        if (addressRepository.findById(id).isPresent()) {
            if (!milestoneService.findByAddressId(id).isEmpty())
                throw new BadHttpRequest();
            addressRepository.deleteById(id);
        }
    }

    @Transactional
    public void deleteAll() {
        addressRepository.deleteAll();
    }

    @Transactional
    public Address modifyAddress(Address address) {
        if (!addressRepository.existsById(address.getId()))
            throw new EntityNotFoundException();
        return addressRepository.save(address);
    }

    public Page<Address> findAddressesByExample(AddressExampleDto example, Pageable pageable) {
        String countryCode = example.getCountryCode();
        String city = example.getCity();
        String street = example.getStreet();
        String zipCode = example.getZipCode();

        Specification<Address> spec = Specification.where(null);
        if (StringUtils.hasText(countryCode))
            spec = spec.and(AddressSpecifications.hasCountryCode(countryCode));
        if (StringUtils.hasText(city))
            spec = spec.and(AddressSpecifications.hasCity(city));
        if (StringUtils.hasText(street))
            spec = spec.and(AddressSpecifications.hasStreet(street));
        if (StringUtils.hasText(zipCode))
            spec = spec.and(AddressSpecifications.hasZipCode(zipCode));

        return addressRepository.findAll(spec, pageable);
    }

}
