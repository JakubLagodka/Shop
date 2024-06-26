package bench.artshop.order.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDto {
    private Long orderId;
    private String productCode;
    private Integer quantity;
    private String customerComment;
    private CustomerDto customer;
}
