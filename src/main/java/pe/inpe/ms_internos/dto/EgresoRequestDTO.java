package pe.inpe.ms_internos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EgresoRequestDTO {

    @NotBlank(message = "El motivo de egreso es obligatorio")
    private String motivo;
}
