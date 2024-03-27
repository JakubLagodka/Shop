package bench.artshop.order.services;

import bench.artshop.order.dao.Customer;
import bench.artshop.order.dao.DeliveryAddress;
import bench.artshop.order.dao.Order;
import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.mapper.CustomerMapper;
import bench.artshop.order.mapper.DeliveryAddressMapper;
import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.repository.CustomerRepository;
import bench.artshop.order.repository.DeliveryAddressRepository;
import bench.artshop.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
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

    public List<Order> getOrders() {
//        return List.of(new OrderDto(1L, "sku-kub-glin", 6, "wszystkie w odcieniach zielonego", new CustomerDto("jola@poczta.com", "+48 111 222 333", "Jolanta Ciekawska", new DeliveryAddressDto("Kwiatowa 13", "20-345", "Lublin"), PaymentType.PREPAYMENT)),
//                new OrderDto(544L, "maly-obr-olej", 2, "1 obraz z portetem damy z łasiczką i 1 obraz z widokiem na las", new CustomerDto("tomek@poczta.com", "+48 66 77 888", "Tomasz Kowalski", new DeliveryAddressDto("Lipowa 58", "90-120", "Łódź"), PaymentType.CASH_ON_DELIVERY)));
        return orderRepository.findAll();
    }
    public Order getOrder(Long orderId) {
       return orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
    }

    public Order addOrder(OrderDto orderDto) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.save(deliveryAddressMapper.toDao(orderDto.getCustomer().getDeliveryAddress()));
        Customer customer = customerMapper.toDao(orderDto.getCustomer());
        customer.setDeliveryAddress(deliveryAddress);
        Customer savedCustomer = customerRepository.save(customer);
        Order order = orderMapper.toDao(orderDto);
        order.setCustomer(savedCustomer);
        return orderRepository.save(order);
    }
}
