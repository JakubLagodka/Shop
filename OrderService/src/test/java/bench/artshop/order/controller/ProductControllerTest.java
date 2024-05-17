package bench.artshop.order.controller;

import bench.artshop.order.dao.Product;
import bench.artshop.order.repository.ProductRepository;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Transactional
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldSaveProduct() throws Exception {


        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Product.builder()
                                        .name("pen")
                                        .price(9.99)
                                        .available(true)
                                        .quantity(10)
                                        .createdBy("user")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("pen"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.createdBy").value("user"));
    }

    @Test
    @WithMockUser
    void shouldNotSaveProductWhenUserIsUnauthorized() throws Exception {

        mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Product.builder()
                                .name("pen")
                                .price(9.99)
                                .available(true)
                                .quantity(10)
                                .build())))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateProduct() throws Exception {
        Product save = productRepository.save(Product.builder()
                .name("pen")
                .price(9.99)
                .available(true)
                .quantity(10)
                .build());

        mockMvc.perform(put("/api/product/" +save.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Product.builder()
                        .name("pen")
                        .price(9.99)
                        .available(true)
                        .quantity(10)
                        .createdBy("user")
                        .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("pen"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdDate").exists());
    }

    @Test
    @WithMockUser
    void shouldNotUpdateProductWhenUserIsUnauthorized() throws Exception {
        Product save = productRepository.save(Product.builder()
                .name("pen")
                .price(9.99)
                .available(true)
                .quantity(10)
                .build());

        mockMvc.perform(put("/api/product/1" +save.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(Product.builder()
                                .name("pen")
                                .price(9.99)
                                .available(true)
                                .quantity(10)
                                .build())))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldGetProductByGivenId() throws Exception {
        Product save = productRepository.save(Product.builder()
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

        mockMvc.perform(get("/api/product/" + save.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("pen"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void shouldNotGetProductWhenProductDoesNotExist() throws Exception {

        mockMvc.perform(get("/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteProduct() throws Exception {
        Product save = productRepository.save(Product.builder()
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
        mockMvc.perform(delete("/api/product/" + save.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "john")
    void shouldNotDeleteProductWhenUserIsNotAdmin() throws Exception {
        Product save = productRepository.save(Product.builder()
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
        mockMvc.perform(delete("/api/product/" + save.getId()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotDeleteProductWhenProductDoesNotExist() throws Exception {
        mockMvc.perform(delete("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
