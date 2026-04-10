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
public class InternoUbicacionResponseDTO {

    private Long idUbicacion;
    private Long idInterno;
    private Long idInstitutoSede;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Boolean estadoActual;

    private LocalDateTime registrationDate;
    private String registrationUser;
    private LocalDateTime lastModificationDate;
    private String lastModificationUser;
}