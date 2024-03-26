package bench.artshop.order.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
