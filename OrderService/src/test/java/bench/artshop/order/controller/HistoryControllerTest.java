package bench.artshop.order.controller;

import bench.artshop.order.dao.Product;
import bench.artshop.order.dao.User;
import bench.artshop.order.repository.ProductRepository;
import bench.artshop.order.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org. springframework. test. web. servlet. request. MockMvcRequestBuilders.get;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Transactional
public class HistoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUserHistory() throws Exception {
        User save = userRepository.save(User.builder()
                .firstName("John")
                .lastName("John")
                .login("john")
                .mail("john@gmail.com")
                .password("pass")
                .build());

        mockMvc.perform(get("/api/histories/users/" + save.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .queryParam("page", "0")
                        .queryParam("size", "10"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.content.length()").value(1))
//                .andExpect(jsonPath("$.content[*].id").exists())
//                .andExpect(jsonPath("$.content[*].password").doesNotExist())
//                .andExpect(jsonPath("$.content[*].confirmPassword").doesNotExist())
//                .andExpect(jsonPath("$.content[*].login", containsInAnyOrder("john")))
//                .andExpect(jsonPath("$.content[*].mail", containsInAnyOrder("john@gmail.com")))
//                .andExpect(jsonPath("$.content[*].firstName", containsInAnyOrder("John")))
//                .andExpect(jsonPath("$.content[*].lastName", containsInAnyOrder("John")))
//                .andExpect(jsonPath("$.content[*].createdDate").exists())
//                .andExpect(jsonPath("$.content[*].createdBy", containsInAnyOrder("user")))
//                .andExpect(jsonPath("$.content[*].lastModifiedDate").exists())
//                .andExpect(jsonPath("$.content[*].lastModifiedBy", containsInAnyOrder("user")))
//                .andExpect(jsonPath("$.content[*].revisionType").doesNotExist())
//                .andExpect(jsonPath("$.content[*].revisionNumber").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetProductHistory() throws Exception {
        Product save = productRepository.save(Product.builder()
                .name("pen")
                .price(9.99)
                .available(true)
                .quantity(10)
                .build());

        mockMvc.perform( get("/api/histories/products/" + save.getId())
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.content.length()").value(1))
//                .andExpect(jsonPath("$.content[*].id").exists())
//                .andExpect(jsonPath("$.content[*].price", containsInAnyOrder(9.99)))
//                .andExpect(jsonPath("$.content[*].available", containsInAnyOrder(true)))
//                .andExpect(jsonPath("$.content[*].quantity", containsInAnyOrder(10.0)))
//                .andExpect(jsonPath("$.content[*].name", containsInAnyOrder("pen")))
//                .andExpect(jsonPath("$.content[*].createdBy", containsInAnyOrder("user")))
//                .andExpect(jsonPath("$.content[*].lastModifiedDate").exists())
//                .andExpect(jsonPath("$.content[*].lastModifiedBy", containsInAnyOrder("jan")))
//                .andExpect(jsonPath("$.content[*].revisionType").doesNotExist())
//                .andExpect(jsonPath("$.content[*].revisionNumber").doesNotExist());
    }
}

