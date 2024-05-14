package bench.artshop.order.controller;

import bench.artshop.order.mapper.UserMapper;
import bench.artshop.order.service.UserService;
import bench.artshop.order.util.UserUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static bench.artshop.order.util.UserUtils.userDtos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Transactional
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @BeforeAll
    public static void prepareData() {
        UserUtils.prepareData();
    }

    @Test
    public void shouldFindUsers() {
        var result = userController.findUsers();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(), result.getBody());
    }
    @Test
    public void shouldFindUser() {
        Mockito.when(userMapper.toDto(any())).thenReturn(userDtos.get(0));
        var result = userController.findUser(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userDtos.get(0), result.getBody());
    }
    @Test
    public void shouldNotFindUser() throws Exception {
        mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void shouldMakeUser() {
        var result = userController.saveUser(userDtos.get(0));

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
