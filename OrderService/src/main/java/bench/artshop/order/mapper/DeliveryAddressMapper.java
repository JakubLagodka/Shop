package bench.artshop.order.mapper;

import bench.artshop.order.dao.DeliveryAddress;
import bench.artshop.order.dto.DeliveryAddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryAddressMapper {
    DeliveryAddress toDao(DeliveryAddressDto deliveryAddressDto);

    DeliveryAddressDto toDto(DeliveryAddress deliveryAddress);
}
