package bench.artshop.order.controller;

import bench.artshop.order.dao.Order;
import bench.artshop.order.dao.Product;
import bench.artshop.order.dao.User;
import bench.artshop.order.repository.OrderRepository;
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
import static org. springframework. test. web. servlet. request. MockMvcRequestBuilders.get;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Transactional
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldSaveOrder() throws Exception {
        Product product = productRepository.save(Product.builder()
                .name("pen")
                .price(9.99)
                .available(true)
                .quantity(10)
                .createdBy("user")
                .createdDate(LocalDateTime.of(2022, 1, 5, 12, 40, 50))
                .imageUrl("http:://https://m")
                .lastModifiedBy("jan")
                .lastModifiedDate(LocalDateTime.of(2022, 1, 15, 12, 40, 50))
                .build());

        User user = userRepository.save(User.builder()
                .firstName("John")
                .lastName("John")
                .login("john")
                .mail("john@gmail.com")
                .password("pass")
                .build());


        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Order.builder()
                                .quantity(10)
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").exists());

    }


    @Test
    void shouldNotGetOrderWhenUserIsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/order/1"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }



    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotGetOrderWhenOrderDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/order/1"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }


    @Test
    void shouldNotUpdateOrderWhenUserIsUnauthorized() throws Exception {
        Product product = productRepository.save(Product.builder()
                .name("pen")
                .price(9.99)
                .available(true)
                .quantity(10)
                .createdBy("user")
                .createdDate(LocalDateTime.of(2022, 1, 5, 12, 40, 50))
                .imageUrl("http:://https://m")
                .lastModifiedBy("jan")
                .lastModifiedDate(LocalDateTime.of(2022, 1, 15, 12, 40, 50))
                .build());

        User user = userRepository.save(User.builder()
                .firstName("John")
                .lastName("John")
                .login("john")
                .mail("john@gmail.com")
                .password("pass")
                .build());



        Order order = orderRepository.save(Order.builder()
                .quantity(10)
                .build());
        mockMvc.perform(put("/api/order/" + order.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Order.builder()
                                .quantity(10)
                                .build())))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotUpdateOrderWhenOrderDoesNotExist() throws Exception {
        mockMvc.perform(put("/api/order/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldNotDeleteOrderWhenUserIsUnauthorized() throws Exception {
        Order save = orderRepository.save(Order.builder()
                .quantity(10)
                .build());
        mockMvc.perform(delete("/api/order/" + save.getOrderId()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }

}
