package bench.artshop.order.dao;

import bench.artshop.order.dto.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
