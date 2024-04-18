package bench.artshop.order.services;

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
import bench.artshop.order.util.OrderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static bench.artshop.order.util.OrderUtils.orderDtos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private DeliveryAddressMapper deliveryAddressMapper;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private DeliveryAddressRepository deliveryAddressRepository;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeAll
    public static void prepareData() {
        OrderUtils.prepareData();
       }

    @Test
    public void shouldGetOrder() {
        //given
        Customer customer = Customer.builder()
                .email("jola@poczta.com")
                .phone("+48 111 222 333")
                .fullName("Jolanta Ciekawska")
                .deliveryAddress(DeliveryAddress.builder()
                        .streetWithNumber("Kwiatowa 13")
                        .city("Lublin")
                        .postCode("20-345")
                        .build())
                .paymentType(PaymentType.PREPAYMENT)
                .build();
        Mockito.when(customerMapper.toDao(any())).thenReturn(customer);
        Mockito.when(orderMapper.toDao(any())).thenReturn(Order.builder()
                .orderId(1L)
                .productCode("sku-kub-glin")
                .quantity(6)
                .customerComment("wszystkie w odcieniach zielonego")
                .customer(customer)
                .build());
        List<Order> orders = orderDtos.stream().map(orderDto -> orderMapper.toDao(orderDto)).collect(Collectors.toList());
        Mockito.when(orderService.getOrders()).thenReturn(orders);
        //when
        var result = orderService.getOrders();
        //then
        assertEquals(orders, result);
    }
    @Test
    public void shouldGetEmptyOrder() {
        //given
        //when
        var result = orderService.getOrders();
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldAddOrder() {
        //given
        Order orderMapperDao = orderMapper.toDao(orderDtos.get(0));
        Customer customer = Customer.builder()
                .email("jola@poczta.com")
                .phone("+48 111 222 333")
                .fullName("Jolanta Ciekawska")
                .deliveryAddress(DeliveryAddress.builder()
                        .streetWithNumber("Kwiatowa 13")
                        .city("Lublin")
                        .postCode("20-345")
                        .build())
                .paymentType(PaymentType.PREPAYMENT)
                .build();
        Mockito.when(customerMapper.toDao(any())).thenReturn(customer);
        Mockito.when(orderMapper.toDao(any())).thenReturn(Order.builder()
                .orderId(1L)
                .productCode("sku-kub-glin")
                .quantity(6)
                .customerComment("wszystkie w odcieniach zielonego")
                .customer(customer)
                .build());
        //when
        var result = orderService.addOrder(orderDtos.get(0));

        //then
        assertEquals(orderMapperDao, result);
    }
}