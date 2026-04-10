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
public class ResumenJudicialDTO {

    private Integer totalExpedientes;
    private Integer expedientesActivos;
    private Boolean tieneMandatoActivo;
    private String numeroMandatoActivo;
    private List<String> delitosPrincipales;
}
