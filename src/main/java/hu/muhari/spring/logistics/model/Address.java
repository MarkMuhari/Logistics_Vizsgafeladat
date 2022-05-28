package hu.muhari.spring.logistics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Address {

    @Id
    @GeneratedValue
    private long id;

    private String iso;
    private String city;
    private String street;
    private String zipCode;
    private String streetNumber;

    private Double longitude;
    private Double latitude;

    public Address() {
    }

    public Address(long id, String iso, String city, String street,
                   String zipCode, String streetNumber, Double longitude, Double latitude) {
        this.id = id;
        this.iso = iso;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.streetNumber = streetNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String countryCode) {
        this.iso = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
