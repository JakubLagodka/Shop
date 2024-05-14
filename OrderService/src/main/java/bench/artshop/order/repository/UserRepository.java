package bench.artshop.order.repository;

import bench.artshop.order.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static bench.artshop.order.util.ProblemUtils.getUserWithGivenIdNotFoundProblem;
import static bench.artshop.order.util.ProblemUtils.getUserWithGivenLoginNotFoundProblem;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByLogin(String login);
    public default UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String login) {
                return findByLogin(login)
                        .orElseThrow(() ->getUserWithGivenLoginNotFoundProblem(login));
            }
        };
    }
}
