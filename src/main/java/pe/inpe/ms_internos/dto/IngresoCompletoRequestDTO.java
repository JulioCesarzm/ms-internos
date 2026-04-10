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
public class IngresoCompletoRequestDTO {

    // Datos del interno
    private Long idPersona;
    private String codigoInterno;
    private LocalDate fechaIngreso;

    // Ubicación inicial
    private Long idInstitutoSede;

    // Clasificación inicial (opcional)
    private Long nivelSeguridadId;

    // Para crear expediente judicial (opcional)
    private Boolean crearExpediente;
    private Long estadoProcesalId;
    private List<Long> delitosIds;

    // Observaciones del ingreso
    private String observaciones;
}