package pe.inpe.ms_internos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FichaCompletaResponseDTO {

    // Datos básicos del interno
    private Long idInterno;
    private String codigoInterno;
    private LocalDate fechaIngreso;
    private Boolean estado;

    // Datos personales de ms-persona
    private PersonaResumenDTO persona;

    // Ubicacion actual
    private InternoUbicacionResponseDTO ubicacionActual;

    // Clasificacion actual
    private ClasificacionInternoResponseDTO clasificacionActual;

    // ultimos registrosd
    private List<RegistroInternoResponseDTO> ultimosRegistros;

    // Resumen judicial de ms-judicia
    private ResumenJudicialDTO resumenJudicial;

    // Puede ser trasladado?
    private Boolean puedeSerTrasladado;
    private String motivoRestriccion;

    // Fecha
    private LocalDateTime fechaConsulta;
}