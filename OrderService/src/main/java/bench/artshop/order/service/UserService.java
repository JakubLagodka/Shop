package bench.artshop.order.service;

import bench.artshop.order.dao.User;
import bench.artshop.order.repository.UserRepository;
import bench.artshop.order.util.ProblemUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static bench.artshop.order.util.ProblemUtils.getUserWithGivenIdNotFoundProblem;
import static bench.artshop.order.util.SecurityUtils.getCurrentUserLogin;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //    private final PasswordEncoder passwordEncoder;
    public User create(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public User update(User user, Long id) {
        User userDb = getById(id);
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());

        return userDb;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> getUserWithGivenIdNotFoundProblem(id));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getCurrentUser() {
        return userRepository.findByLogin(getCurrentUserLogin())
                .orElseThrow(ProblemUtils::getUserNotFoundProblem);
    }
}