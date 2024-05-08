package bench.artshop.order.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static bench.artshop.order.util.ProblemUtils.getOrderNotFoundProblem;

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
        return orderRepository.findAll();
    }
    public Order getOrder(Long orderId) {
       return orderRepository.findById(orderId).orElseThrow(()->getOrderNotFoundProblem(orderId));
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
