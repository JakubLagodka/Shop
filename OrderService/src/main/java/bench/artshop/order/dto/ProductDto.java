package bench.artshop.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.history.RevisionMetadata;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @PositiveOrZero
    private Double price;
    @NotNull
    private boolean available;
    @PositiveOrZero
    private double quantity;

    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime lastModifiedDate;

    private String lastModifiedBy;

    private RevisionMetadata.RevisionType revisionType;

    private Integer revisionNumber;

    private String imageUrl;

}
