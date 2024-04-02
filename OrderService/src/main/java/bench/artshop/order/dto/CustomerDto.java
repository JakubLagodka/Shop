package bench.artshop.order.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CustomerDto {
    private Long customerId;
    private String email;
    private String phone;
    private String fullName;
    private DeliveryAddressDto deliveryAddress;
    private PaymentType paymentType;
}
