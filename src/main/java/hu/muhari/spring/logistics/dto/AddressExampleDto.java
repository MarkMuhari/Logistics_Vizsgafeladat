package hu.muhari.spring.logistics.dto;

public class AddressExampleDto {

    private String countryCode;
    private String city;
    private String street;
    private String zipCode;

    public AddressExampleDto() {
    }

    public AddressExampleDto(String countryCode, String city, String street, String zipCode) {
        this.countryCode = countryCode;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
}
