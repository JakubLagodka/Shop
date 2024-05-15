package bench.artshop.order.mapper;

import bench.artshop.order.dao.Role;
import bench.artshop.order.repository.RoleRepository;
import bench.artshop.order.util.ProblemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> StringToRoleList(String input) {
        return Arrays.stream(input.split(","))
                .map(role -> roleRepository.findByName(role)
                        .orElseThrow(() -> ProblemUtils.getRoleWithGivenNameNotFoundProblem(role)))
                .collect(Collectors.toList());
    }

    public String RoleListToString(List<Role> input) {
        return input.stream().map(Role::getName).collect(Collectors.joining(","));
    }
}
