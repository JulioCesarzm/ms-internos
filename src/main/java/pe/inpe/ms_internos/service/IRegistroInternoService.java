package pe.inpe.ms_internos.service;

import pe.inpe.ms_internos.dto.RegistroInternoRequestDTO;
import pe.inpe.ms_internos.dto.RegistroInternoResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IRegistroInternoService {

    // Registrar movimiento
    RegistroInternoResponseDTO registrar(RegistroInternoRequestDTO request);

    // Consultas
    List<RegistroInternoResponseDTO> obtenerPorInterno(Long idInterno);

    List<RegistroInternoResponseDTO> obtenerPorTipo(Long idInterno, Long tipoRegistroId);

    RegistroInternoResponseDTO obtenerUltimoRegistro(Long idInterno);

    List<RegistroInternoResponseDTO> obtenerPorRangoFechas(Long idInterno, LocalDateTime inicio, LocalDateTime fin);

    // Para reportes
    List<RegistroInternoResponseDTO> obtenerIngresosRecientes(Long tipoRegistroId, LocalDateTime desde);
}