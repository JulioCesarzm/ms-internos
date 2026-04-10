package pe.inpe.ms_internos.service;

import pe.inpe.ms_internos.dto.InternoUbicacionRequestDTO;
import pe.inpe.ms_internos.dto.InternoUbicacionResponseDTO;

import java.util.List;

public interface IInternoUbicacionService {

    // Gestión de ubicaciones
    InternoUbicacionResponseDTO asignarUbicacion(InternoUbicacionRequestDTO request);

    InternoUbicacionResponseDTO cambiarUbicacion(Long idInterno, Long nuevaSedeId, String observaciones);

    // Consultas
    InternoUbicacionResponseDTO obtenerUbicacionActual(Long idInterno);

    List<InternoUbicacionResponseDTO> obtenerHistorialUbicaciones(Long idInterno);

    List<InternoUbicacionResponseDTO> obtenerInternosEnSede(Long idSede);

    Long contarInternosEnSede(Long idSede);

    // Validaciones
    boolean estaEnSede(Long idInterno, Long idSede);
}