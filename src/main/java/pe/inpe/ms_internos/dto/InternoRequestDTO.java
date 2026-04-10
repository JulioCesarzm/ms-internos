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
public class InternoRequestDTO {

    private Long idPersona;
    private String codigoInterno;
    private LocalDate fechaIngreso;
    private Boolean estado;
}