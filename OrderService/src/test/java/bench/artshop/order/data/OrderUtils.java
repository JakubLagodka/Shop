package bench.artshop.order.data;

import bench.artshop.order.dto.CustomerDto;
import bench.artshop.order.dto.DeliveryAddressDto;
import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.dto.PaymentType;

import java.util.ArrayList;
import java.util.List;

public class OrderUtils {
    public static List<OrderDto> orderDtos = new ArrayList<>();

    public static void prepareData() {
        orderDtos.add(new OrderDto(543L, "sku-kub-glin", 6, "wszystkie w odcieniach zielonego", new CustomerDto(1L,"jola@poczta.com", "+48 111 222 333", "Jolanta Ciekawska", new DeliveryAddressDto(1L,"Kwiatowa 13", "20-345", "Lublin"), PaymentType.PREPAYMENT)));

        orderDtos.add(new OrderDto(544L, "maly-obr-olej", 2, "1 obraz z portetem damy z łasiczką i 1 obraz z widokiem na las", new CustomerDto(1L,"tomek@poczta.com", "+48 66 77 888", "Tomasz Kowalski", new DeliveryAddressDto(1L,"Lipowa 58", "90-120", "Łódź"), PaymentType.CASH_ON_DELIVERY)));
    }

}
