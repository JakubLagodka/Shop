package bench.artshop.order.mapper;

import bench.artshop.order.dao.User;
import bench.artshop.order.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    @Mapping(target = "roles", source = "role")
    User toDao(UserDto userDto);

    @Mapping(target = "role", source = "roles")
    UserDto toDto(User user);
}
