package pe.inpe.ms_internos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleRegistroResponseDTO {

    private Long idDetalle;
    private Long idRegistro;
    private String descripcion;
    private String valor;

    private LocalDateTime registrationDate;
    private String registrationUser;
}
