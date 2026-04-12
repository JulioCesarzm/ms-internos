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
public class MandatoActivoDTO {

    private Long idMandato;
    private String numeroMandato;
    private String autoridadEmisora;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
}