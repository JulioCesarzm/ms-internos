package pe.inpe.ms_internos.mapper.Client;

import org.mapstruct.Mapper;
import pe.inpe.ms_internos.dto.ExpedienteResponseDTO;
import pe.inpe.ms_internos.dto.MandatoActivoDTO;
import pe.inpe.ms_internos.dto.ResumenJudicialDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IExpedienteClientMapper {

    default ResumenJudicialDTO toResumenJudicial(List<ExpedienteResponseDTO> expedientes,
                                                 MandatoActivoDTO mandatoActivo) {
        if (expedientes == null) expedientes = List.of();

        long activos = expedientes.stream()
                .filter(e -> e.getFechaFin() == null)
                .count();

        boolean tieneMandato = mandatoActivo != null;

        return ResumenJudicialDTO.builder()
                .totalExpedientes(expedientes.size())
                .expedientesActivos((int) activos)
                .tieneMandatoActivo(tieneMandato)
                .numeroMandatoActivo(tieneMandato ? mandatoActivo.getNumeroMandato() : null)
                .delitosPrincipales(expedientes.isEmpty() ? List.of() : List.of("Consultar en ms-judicial"))
                .build();
    }

    default ResumenJudicialDTO emptyResumenJudicial() {
        return ResumenJudicialDTO.builder()
                .totalExpedientes(0)
                .expedientesActivos(0)
                .tieneMandatoActivo(false)
                .delitosPrincipales(List.of())
                .build();
    }
}