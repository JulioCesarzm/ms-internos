package pe.inpe.ms_internos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SedeResumenDTO {
    private Long idInstitutoSede;
    private String nombreSede;
    private Integer capacidad;
}