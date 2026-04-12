package pe.inpe.ms_internos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SedeResponseDTO {
    private Long idInstitutoSede;
    private Long institutoId;
    private String nombreInstituto;
    private String nombre;
    private String codigo;
    private Integer capacidad;
    private Boolean estado;
}