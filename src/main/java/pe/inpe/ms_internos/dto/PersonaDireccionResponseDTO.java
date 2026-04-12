package pe.inpe.ms_internos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDireccionResponseDTO {

    private Long idPersonaDireccion;
    private Long idLocation;
    private String direccion;
    private String referencia;
    private Boolean esPrincipal;
}
