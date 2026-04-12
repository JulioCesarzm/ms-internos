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
public class ParameterTableResponseDTO {
    private Long idParameter;
    private Integer tipoParametro;
    private String codigo;
    private String descripcion;
    private String valorExtra;
    private Boolean estado;
    private LocalDateTime registrationDate;
    private String registrationUser;
}