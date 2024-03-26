package bench.artshop.order.mapper;

import bench.artshop.order.dao.Order;
import bench.artshop.order.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toDao(OrderDto orderDto);

    OrderDto toDto(Order order);
}
