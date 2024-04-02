package bench.artshop.order.controllers;

import bench.artshop.order.dao.Customer;
import bench.artshop.order.dao.DeliveryAddress;
import bench.artshop.order.dao.Order;
import bench.artshop.order.dto.PaymentType;
import bench.artshop.order.mapper.CustomerMapper;
import bench.artshop.order.mapper.DeliveryAddressMapper;
import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.repository.CustomerRepository;
import bench.artshop.order.repository.DeliveryAddressRepository;
import bench.artshop.order.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;
    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;
    @Test
    public void testGetOrders() throws Exception {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.save(DeliveryAddress.builder()
                .streetWithNumber("Kwiatowa 13")
                .city("Lublin")
                .postCode("20-345")
                .build());
        Customer customer = customerRepository.save(Customer.builder()
                .email("jola@poczta.com")
                .phone("+48 111 222 333")
                .fullName("Jolanta Ciekawska")
                .deliveryAddress(deliveryAddress)
                .paymentType(PaymentType.PREPAYMENT)
                .build());
        orderRepository.save(Order.builder()
                .orderId(1L)
                .productCode("sku-kub-glin")
                .quantity(6)
                .customerComment("wszystkie w odcieniach zielonego")
                .customer(customer)
                .build());

        mockMvc.perform(get("/order/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(6));
    }

    @Test
    public void testGetOrder() throws Exception {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.save(DeliveryAddress.builder()
                .streetWithNumber("Kwiatowa 13")
                .city("Lublin")
                .postCode("20-345")
                .build());
        Customer customer = customerRepository.save(Customer.builder()
                .email("jola@poczta.com")
                .phone("+48 111 222 333")
                .fullName("Jolanta Ciekawska")
                .deliveryAddress(deliveryAddress)
                .paymentType(PaymentType.PREPAYMENT)
                .build());
        orderRepository.save(Order.builder()
                .orderId(1L)
                .productCode("sku-kub-glin")
                .quantity(6)
                .customerComment("wszystkie w odcieniach zielonego")
                .customer(customer)
                .build());

        mockMvc.perform(get("/order/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(6));
    }
    @Test
    public void testAddOrder() throws Exception {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.save(DeliveryAddress.builder()
                .streetWithNumber("Kwiatowa 13")
                .city("Lublin")
                .postCode("20-345")
                .build());
        Customer customer = customerRepository.save(Customer.builder()
                .email("jola@poczta.com")
                .phone("+48 111 222 333")
                .fullName("Jolanta Ciekawska")
                .deliveryAddress(deliveryAddress)
                .paymentType(PaymentType.PREPAYMENT)
                .build());
        orderRepository.save(Order.builder()
                .orderId(1L)
                .productCode("sku-kub-glin")
                .quantity(6)
                .customerComment("wszystkie w odcieniach zielonego")
                .customer(customer)
                .build());
        mockMvc.perform(post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Order.builder()
                                .orderId(1L)
                                .productCode("sku-kub-glin")
                                .quantity(6)
                                .customerComment("wszystkie w odcieniach zielonego")
                                .customer(customer)
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(6));
    }
}