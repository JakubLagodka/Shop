package bench.artshop.order.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class CustomerDto {
    private String email;
    private String phone;
    private String fullName;
    private DeliveryAddressDto deliveryAddress;
    private PaymentType paymentType;
}
