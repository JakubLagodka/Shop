package bench.artshop.order.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class OrderDto {
    private Long orderId;
    private String description;
}
