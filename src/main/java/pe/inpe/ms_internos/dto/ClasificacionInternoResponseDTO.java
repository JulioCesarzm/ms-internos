package pe.inpe.ms_internos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClasificacionInternoResponseDTO {

    private Long idClasificacion;
    private Long idInterno;
    private Long nivelSeguridadId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private LocalDateTime registrationDate;
    private String registrationUser;
    private LocalDateTime lastModificationDate;
    private String lastModificationUser;
}