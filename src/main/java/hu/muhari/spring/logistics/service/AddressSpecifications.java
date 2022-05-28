package hu.muhari.spring.logistics.service;

import hu.muhari.spring.logistics.model.Address;
import hu.muhari.spring.logistics.model.Address_;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecifications {

    public static Specification<Address> hasCountryCode(String countryCode) {
        return (root, cq, cb) -> cb.equal(root.get(Address_.iso), countryCode);
    }

    public static Specification<Address> hasCity(String city) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.city)),
                (city + "%").toLowerCase());
    }

    public static Specification<Address> hasStreet(String street) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.street)),
                (street + "%").toLowerCase());
    }

    public static Specification<Address> hasZipCode(String zipCode) {
        return (root, cq, cb) -> cb.equal(root.get(Address_.zipCode), zipCode);
    }

}
