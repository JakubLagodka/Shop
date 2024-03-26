package bench.artshop.order.dao;

import bench.artshop.order.dto.PaymentType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue
    private Long customerId;
    private String email;
    private String phone;
    private String fullName;
    @ManyToOne
    private DeliveryAddress deliveryAddress;
    private PaymentType paymentType;
}
