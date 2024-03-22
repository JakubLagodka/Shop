package bench.artshop.order.services;

import bench.artshop.order.dto.CustomerDto;
import bench.artshop.order.dto.DeliveryAddressDto;
import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.dto.PaymentType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    public List<OrderDto> getOrders(){
       return List.of(new OrderDto(543L,"sku-kub-glin",6,"wszystkie w odcieniach zielonego",new CustomerDto("jola@poczta.com","+48 111 222 333","Jolanta Ciekawska",new DeliveryAddressDto("Kwiatowa 13","20-345","Lublin"), PaymentType.PREPAYMENT)),
                new OrderDto(544L,"maly-obr-olej",2,"1 obraz z portetem damy z łasiczką i 1 obraz z widokiem na las",new CustomerDto("tomek@poczta.com","+48 66 77 888","Tomasz Kowalski",new DeliveryAddressDto("Lipowa 58","90-120","Łódź"), PaymentType.CASH_ON_DELIVERY)));
    }
}
