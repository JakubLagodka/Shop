package bench.artshop.order.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deliveryAddresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddress {
    @Id
    @GeneratedValue
    private Long addressId;
    private String streetWithNumber;
    private String postCode;
    private String city;
}
