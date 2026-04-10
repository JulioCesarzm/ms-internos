package pe.inpe.ms_internos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClasificacionInternoRequestDTO {

    private Long idInterno;
    private Long nivelSeguridadId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}