package bench.artshop.order.service;

import bench.artshop.order.dao.Role;
import bench.artshop.order.dao.User;
import bench.artshop.order.repository.RoleRepository;
import bench.artshop.order.repository.UserRepository;
import bench.artshop.order.util.ProblemUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RoleService {

    @Autowired
    private final RoleRepository RoleRepository;
    @Autowired
    private final UserRepository userRepository;

    public Role create(Role Role) {
        RoleRepository.findByName(Role.getName())
                .ifPresentOrElse(Role1 -> {
                    throw ProblemUtils.getRoleWithGivenNameAlreadyExistsProblem(Role.getName());
                }, () -> RoleRepository.save(Role));
        return Role;
    }

    public Role update(Role Role, Long id) {
        Role RoleDb = getById(id);
        RoleDb.setName(Role.getName());

        return RoleDb;
    }

    public void delete(Long id) {
        RoleRepository.deleteById(id);
    }

    public Role getById(Long id) {
        return RoleRepository.findById(id).orElseThrow(() -> ProblemUtils.getRoleWithGivenIdNotFoundProblem(id));
    }

    public List<Role> getRoles() {
        return RoleRepository.findAll();
    }

    public List<Role> getCurrentRoles(String currentUserLogin) {
        User user = userRepository.findByLogin(currentUserLogin)
                .orElseThrow(() -> ProblemUtils.getUserWithGivenLoginNotFoundProblem(currentUserLogin));
       return user.getRoles();
    }
}
