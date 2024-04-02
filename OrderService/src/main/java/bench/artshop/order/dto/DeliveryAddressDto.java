package bench.artshop.order.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DeliveryAddressDto {
    private Long addressId;
    private String streetWithNumber;
    private String postCode;
    private String city;
}
