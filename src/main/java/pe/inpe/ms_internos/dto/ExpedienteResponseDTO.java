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
public class ExpedienteResponseDTO {

    private Long idExpediente;
    private Long idInterno;
    private String numeroExpediente;
    private Long estadoProcesalId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String observaciones;
}