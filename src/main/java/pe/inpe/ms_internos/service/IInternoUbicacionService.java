package pe.inpe.ms_internos.service;

import pe.inpe.ms_internos.dto.InternoUbicacionRequestDTO;
import pe.inpe.ms_internos.dto.InternoUbicacionResponseDTO;

import java.util.List;

public interface IInternoUbicacionService {

    InternoUbicacionResponseDTO asignarUbicacion(InternoUbicacionRequestDTO request);

    InternoUbicacionResponseDTO cambiarUbicacion(Long idInterno, Long nuevaSedeId, String observaciones);

    InternoUbicacionResponseDTO obtenerUbicacionActual(Long idInterno);

    List<InternoUbicacionResponseDTO> obtenerHistorialUbicaciones(Long idInterno);

    List<InternoUbicacionResponseDTO> obtenerInternosEnSede(Long idSede);

    Long contarInternosEnSede(Long idSede);

    boolean estaEnSede(Long idInterno, Long idSede);
}