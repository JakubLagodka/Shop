package bench.artshop.order.dao;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue
    private Long orderId;
    private String productCode;
    private Integer quantity;
    private String customerComment;
    @ManyToOne
    private Customer customer;
}
