package bench.artshop.order.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class DeliveryAddressDto {
    private String streetWithNumber;
    private String postCode;
    private String city;
}
