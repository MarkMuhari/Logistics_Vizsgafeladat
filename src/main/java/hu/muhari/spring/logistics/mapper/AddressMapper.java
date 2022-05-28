package hu.muhari.spring.logistics.mapper;

import java.util.List;

import hu.muhari.spring.logistics.dto.AddressDto;
import hu.muhari.spring.logistics.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    List<AddressDto> addressesToDtos(List<Address> addresses);

    AddressDto addressToDto(Address address);

    List<Address> dtosToAddresses(List<AddressDto> addressDtos);

    Address dtoToAddress(AddressDto addressDto);
}
