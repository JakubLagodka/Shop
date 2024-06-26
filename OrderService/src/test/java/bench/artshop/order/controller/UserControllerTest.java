package bench.artshop.order.controller;

import bench.artshop.order.dao.User;
import bench.artshop.order.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Transactional
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldNotGetUserWhenUserIsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldNotGetUserWhenUserHasNotAccess() throws Exception {
        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotUpdateUserWhenUserDoesNotExist() throws Exception {
        mockMvc.perform(put("/api/user/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }
    @Test
    @WithMockUser(username = "john")
    void shouldNotDeleteUserWhenUserIsNotAdmin() throws Exception {
        User save = userRepository.save(User.builder()
                .firstName("John")
                .lastName("John")
                .login("john")
                .mail("john@gmail.com")
                .password("pass")
                .build());
        mockMvc.perform(delete("/api/user/" + save.getId()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }

}
