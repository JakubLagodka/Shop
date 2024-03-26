package bench.artshop.order.services;

import bench.artshop.order.dto.CustomerDto;
import bench.artshop.order.dto.DeliveryAddressDto;
import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.dto.PaymentType;
import bench.artshop.order.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    static List<OrderDto> orderDtos = new ArrayList<>();
    @BeforeAll
    public static void prepareData() {
        orderDtos.add(new OrderDto(1L, "sku-kub-glin", 6, "wszystkie w odcieniach zielonego", new CustomerDto("jola@poczta.com", "+48 111 222 333", "Jolanta Ciekawska", new DeliveryAddressDto("Kwiatowa 13", "20-345", "Lublin"), PaymentType.PREPAYMENT)));

        orderDtos.add(new OrderDto(544L, "maly-obr-olej", 2, "1 obraz z portetem damy z łasiczką i 1 obraz z widokiem na las", new CustomerDto("tomek@poczta.com", "+48 66 77 888", "Tomasz Kowalski", new DeliveryAddressDto("Lipowa 58", "90-120", "Łódź"), PaymentType.CASH_ON_DELIVERY)));
    }

    @Test
    public void shouldProductsBeIdentical() {
        //given
        //when
        var result = orderService.getOrders();
        //then
        assertEquals(orderDtos, result);
    }

    @Test
    public void shouldAddOrder() {
        //given
        //when
        var result = orderMapper.toDto(orderService.addOrder(OrderDto.builder()
                        .orderId(543L)
                        .productCode("sku-kub-glin")
                        .quantity(6)
                        .customerComment("wszystkie w odcieniach zielonego")
                        .customer(CustomerDto.builder()
                                .email("jola@poczta.com")
                                .phone("+48 111 222 333")
                                .fullName("Jolanta Ciekawska")
                                .deliveryAddress(DeliveryAddressDto.builder()
                                        .streetWithNumber("Kwiatowa 13")
                                        .city("Lublin")
                                        .postCode("20-345")
                                        .build())
                                .paymentType(PaymentType.PREPAYMENT)
                                .build())
                .build()));
        //then
        assertEquals(orderDtos.get(0), result);
    }
}