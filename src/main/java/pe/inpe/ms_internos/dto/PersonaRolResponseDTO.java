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
public class PersonaRolResponseDTO {

    private Long idPersonaRol;
    private Long idPersona;
    private Long tipoPersonaRolId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean estado;
    private String observaciones;
}