package pe.inpe.ms_internos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SedeDisponibilidadDTO {

    private Long sedeId;
    private String nombreSede;
    private Integer capacidadTotal;
    private Integer ocupados;
    private Integer disponibles;
    private Boolean estado;
}