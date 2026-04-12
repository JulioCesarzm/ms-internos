package pe.inpe.ms_internos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpedienteRequestDTO {

    private Long idInterno;
    private Long estadoProcesalId;
    private LocalDate fechaInicio;
    private String observaciones;
    private String descripcionHistorialInicial;
    private List<Long> delitosIds;
}