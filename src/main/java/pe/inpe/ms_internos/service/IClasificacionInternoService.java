package pe.inpe.ms_internos.service;

import pe.inpe.ms_internos.dto.ClasificacionInternoRequestDTO;
import pe.inpe.ms_internos.dto.ClasificacionInternoResponseDTO;

import java.util.List;

public interface IClasificacionInternoService {

    ClasificacionInternoResponseDTO asignarClasificacion(ClasificacionInternoRequestDTO request);

    ClasificacionInternoResponseDTO cambiarClasificacion(Long idInterno, Long nuevoNivelSeguridadId);

    ClasificacionInternoResponseDTO obtenerClasificacionActiva(Long idInterno);

    List<ClasificacionInternoResponseDTO> obtenerHistorialClasificaciones(Long idInterno);

    List<ClasificacionInternoResponseDTO> obtenerInternosPorNivelSeguridad(Long nivelSeguridadId);
}