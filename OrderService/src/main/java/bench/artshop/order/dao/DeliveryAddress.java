package bench.artshop.order.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "deliveryAddresses")
@Data
public class DeliveryAddress {
    @Id
    @GeneratedValue
    private Long addressId;
    private String streetWithNumber;
    private String postCode;
    private String city;
}
