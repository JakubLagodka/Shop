package bench.artshop.order.service;

import bench.artshop.order.dao.User;
import bench.artshop.order.mapper.UserMapper;
import bench.artshop.order.repository.UserRepository;
import bench.artshop.order.util.UserUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static bench.artshop.order.util.UserUtils.userDtos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @BeforeAll
    public static void prepareData() {
        UserUtils.prepareData();
       }

    @Test
    public void shouldGetUser() {
        //given
        List<User> users = userDtos.stream().map(userDto -> userMapper.toDao(userDto)).toList();
        Mockito.when(userService.getUsers()).thenReturn(users);
        //when
        var result = userService.getUsers();
        //then
        assertEquals(users, result);
    }
    @Test
    public void shouldGetEmptyUser() {
        //given
        //when
        var result = userService.getUsers();
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldAddUser() {
        //given
       User user = User.builder()
               .firstName("Jan")
               .lastName("Kowalski")
               .login("jan")
               .mail("jan@gmail.com")
               .password("password")
               .build();
        Mockito.when(userService.create(user)).thenReturn(user);
        //when
        var result = userService.create(user);

        //then
        assertEquals(user, result);
    }
}