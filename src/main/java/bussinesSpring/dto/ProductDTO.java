package bussinesSpring.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDTO {
    private Long id;

    @NotNull(message = "Campo obligatorio")
    @Size(min = 3, message = "Mínimo 3 caracteres")
    private String name;

    private String description;

    private Double price;




}
