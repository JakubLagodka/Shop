package bench.artshop.order.mapper;

import bench.artshop.order.dao.Customer;
import bench.artshop.order.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toDao(CustomerDto customerDto);

    CustomerDto toDto(Customer customer);
}
