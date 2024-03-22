package bench.artshop.order.services;

import bench.artshop.order.dto.CustomerDto;
import bench.artshop.order.dto.DeliveryAddressDto;
import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.dto.PaymentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    static List<OrderDto> orderDtos = new ArrayList<>();

    @BeforeAll
    public static void prepareData() {
        orderDtos.add(new OrderDto(543L, "sku-kub-glin", 6, "wszystkie w odcieniach zielonego", new CustomerDto("jola@poczta.com", "+48 111 222 333", "Jolanta Ciekawska", new DeliveryAddressDto("Kwiatowa 13", "20-345", "Lublin"), PaymentType.PREPAYMENT)));

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
}