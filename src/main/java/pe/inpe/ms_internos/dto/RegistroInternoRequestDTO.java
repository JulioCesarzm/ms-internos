package pe.inpe.ms_internos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroInternoRequestDTO {

    private Long idInterno;
    private Long idInstitutoSede;
    private Long tipoRegistroId;
    private String observaciones;
    private List<DetalleRegistroRequestDTO> detalles;
}