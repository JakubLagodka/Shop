package bench.artshop.order.mapper;

import bench.artshop.order.dao.Product;
import bench.artshop.order.dto.ProductDto;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toDao( ProductDto productDto);

    ProductDto toDto(Product product);
}
