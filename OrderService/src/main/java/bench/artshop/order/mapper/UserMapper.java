package bench.artshop.order.mapper;

import bench.artshop.order.dao.User;
import bench.artshop.order.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDao(UserDto userDto);
    UserDto toDto(User user);
}
