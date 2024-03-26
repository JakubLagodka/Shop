package bench.artshop.order.controllers;

import bench.artshop.order.dao.Customer;
import bench.artshop.order.dao.DeliveryAddress;
import bench.artshop.order.dao.Order;
import bench.artshop.order.dto.CustomerDto;
import bench.artshop.order.dto.DeliveryAddressDto;
import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.dto.PaymentType;
import bench.artshop.order.mapper.CustomerMapper;
import bench.artshop.order.mapper.DeliveryAddressMapper;
import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.repository.CustomerRepository;
import bench.artshop.order.repository.DeliveryAddressRepository;
import bench.artshop.order.repository.OrderRepository;
import bench.artshop.order.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    @Mock
    private OrderService orderService;
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

    static List<OrderDto> orders = new ArrayList<>();
    @Test
    public void testGetProducts() throws Exception {

        Mockito.when(orderService.getOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(543L));
    }

    @Test
    public void testAddOrder() throws Exception {

        Mockito.when(orderService.getOrders()).thenReturn(orders);

        DeliveryAddress deliveryAddress = deliveryAddressRepository.save(deliveryAddressMapper.toDao(DeliveryAddressDto.builder()
                .streetWithNumber("Kwiatowa 13")
                .city("Lublin")
                .postCode("20-345")
                .build()));
        Customer customer = customerRepository.save(customerMapper.toDao(CustomerDto.builder()
                .email("jola@poczta.com")
                .phone("+48 111 222 333")
                .fullName("Jolanta Ciekawska")
                .deliveryAddress(deliveryAddressMapper.toDto(deliveryAddress))
                .paymentType(PaymentType.PREPAYMENT)
                .build()));
        Order build = orderRepository.save(orderMapper.toDao(OrderDto.builder()
                .orderId(1L)
                .productCode("sku-kub-glin")
                .quantity(6)
                .customerComment("wszystkie w odcieniach zielonego")
                .customer(customerMapper.toDto(customer))
                .build()));
        mockMvc.perform(post("/orders/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderDto.builder()
                                .orderId(1L)
                                .productCode("sku-kub-glin")
                                .quantity(6)
                                .customerComment("wszystkie w odcieniach zielonego")
                                .customer(customerMapper.toDto(customer))
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L));
    }
}