package pe.inpe.ms_internos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.inpe.ms_internos.dto.ClasificacionInternoRequestDTO;
import pe.inpe.ms_internos.dto.ClasificacionInternoResponseDTO;
import pe.inpe.ms_internos.entity.ClasificacionInterno;
import pe.inpe.ms_internos.exception.BadRequestException;
import pe.inpe.ms_internos.exception.ResourceNotFoundException;
import pe.inpe.ms_internos.mapper.IClasificacionInternoMapper;
import pe.inpe.ms_internos.repository.IClasificacionInternoRepository;
import pe.inpe.ms_internos.repository.IInternoRepository;
import pe.inpe.ms_internos.service.IClasificacionInternoService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClasificacionInternoServiceImpl implements IClasificacionInternoService {

    private final IClasificacionInternoRepository clasificacionRepository;
    private final IInternoRepository internoRepository;
    private final IClasificacionInternoMapper clasificacionMapper;

    @Override
    @Transactional
    public ClasificacionInternoResponseDTO asignarClasificacion(ClasificacionInternoRequestDTO request) {
        log.info("Asignando clasificación a interno: {}", request.getIdInterno());

        // Validaciones
        if (request.getIdInterno() == null) {
            throw new BadRequestException("El ID del interno es obligatorio");
        }
        if (request.getNivelSeguridadId() == null) {
            throw new BadRequestException("El nivel de seguridad es obligatorio");
        }

        // Verificar que el interno existe
        if (!internoRepository.existsById(request.getIdInterno())) {
            throw new ResourceNotFoundException("Interno no encontrado con ID: " + request.getIdInterno());
        }

        // Cerrar clasificación activa si existe
        clasificacionRepository.cerrarClasificacionActiva(request.getIdInterno());

        // Crear nueva clasificación
        ClasificacionInterno clasificacion = clasificacionMapper.toEntity(request);
        clasificacion.setFechaInicio(LocalDate.now());
        clasificacion = clasificacionRepository.save(clasificacion);

        log.info("Clasificación asignada con ID: {}", clasificacion.getIdClasificacion());
        return clasificacionMapper.toResponse(clasificacion);
    }

    @Override
    @Transactional
    public ClasificacionInternoResponseDTO cambiarClasificacion(Long idInterno, Long nuevoNivelSeguridadId) {
        log.info("Cambiando clasificación del interno {} a nivel: {}", idInterno, nuevoNivelSeguridadId);

        // Cerrar clasificación activa
        clasificacionRepository.cerrarClasificacionActiva(idInterno);

        // Crear nueva clasificación
        ClasificacionInterno nuevaClasificacion = new ClasificacionInterno();
        nuevaClasificacion.setIdInterno(idInterno);
        nuevaClasificacion.setNivelSeguridadId(nuevoNivelSeguridadId);
        nuevaClasificacion.setFechaInicio(LocalDate.now());
        nuevaClasificacion = clasificacionRepository.save(nuevaClasificacion);

        log.info("Clasificación cambiada. Nueva clasificación ID: {}", nuevaClasificacion.getIdClasificacion());
        return clasificacionMapper.toResponse(nuevaClasificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public ClasificacionInternoResponseDTO obtenerClasificacionActiva(Long idInterno) {
        log.info("Obteniendo clasificación activa del interno: {}", idInterno);
        ClasificacionInterno clasificacion = clasificacionRepository.findClasificacionActivaByInterno(idInterno)
                .orElseThrow(() -> new ResourceNotFoundException("El interno no tiene clasificación activa"));
        return clasificacionMapper.toResponse(clasificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClasificacionInternoResponseDTO> obtenerHistorialClasificaciones(Long idInterno) {
        log.info("Obteniendo historial de clasificaciones del interno: {}", idInterno);
        List<ClasificacionInterno> clasificaciones = clasificacionRepository.findByIdInternoOrderByFechaInicioDesc(idInterno);
        return clasificacionMapper.toResponseList(clasificaciones);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClasificacionInternoResponseDTO> obtenerInternosPorNivelSeguridad(Long nivelSeguridadId) {
        log.info("Obteniendo internos con nivel de seguridad: {}", nivelSeguridadId);
        List<ClasificacionInterno> clasificaciones = clasificacionRepository.findInternosPorNivelSeguridadActivo(nivelSeguridadId);
        return clasificacionMapper.toResponseList(clasificaciones);
    }
}