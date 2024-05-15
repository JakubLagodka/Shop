package bench.artshop.order.service;

import bench.artshop.order.dao.Order;
import bench.artshop.order.repository.CustomerRepository;
import bench.artshop.order.repository.DeliveryAddressRepository;
import bench.artshop.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static bench.artshop.order.util.ProblemUtils.getOrderWithGivenIdNotFoundProblem;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> getOrderWithGivenIdNotFoundProblem(orderId));
    }

    public Order addOrder(Order order) {
        Optional.ofNullable(order.getCustomer())
                .ifPresent(customer -> {
            customer.setDeliveryAddress(deliveryAddressRepository.save(order.getCustomer().getDeliveryAddress()));
            order.setCustomer(customerRepository.save(customer));
        });
        return orderRepository.save(order);
    }

    public Order update(Order dao, Long id) {
        Order orderDb = getById(id);
        orderDb.setQuantity(dao.getQuantity());
        orderDb.setProductCode(dao.getProductCode());

        return orderDb;
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> getOrderWithGivenIdNotFoundProblem(id));
    }
}
