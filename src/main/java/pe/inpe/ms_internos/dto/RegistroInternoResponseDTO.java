package pe.inpe.ms_internos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroInternoResponseDTO {

    private Long idRegistro;
    private Long idInterno;
    private Long idInstitutoSede;
    private LocalDateTime fechaRegistro;
    private Long tipoRegistroId;
    private String observaciones;

    private LocalDateTime registrationDate;
    private String registrationUser;
    private LocalDateTime lastModificationDate;
    private String lastModificationUser;
}
