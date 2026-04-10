package pe.inpe.ms_internos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.inpe.ms_internos.dto.InternoUbicacionRequestDTO;
import pe.inpe.ms_internos.dto.InternoUbicacionResponseDTO;
import pe.inpe.ms_internos.entity.InternoUbicacion;
import pe.inpe.ms_internos.entity.RegistroInterno;
import pe.inpe.ms_internos.exception.BadRequestException;
import pe.inpe.ms_internos.exception.ResourceNotFoundException;
import pe.inpe.ms_internos.mapper.IInternoUbicacionMapper;
import pe.inpe.ms_internos.repository.IInternoRepository;
import pe.inpe.ms_internos.repository.IInternoUbicacionRepository;
import pe.inpe.ms_internos.repository.IRegistroInternoRepository;
import pe.inpe.ms_internos.service.IInternoUbicacionService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternoUbicacionServiceImpl implements IInternoUbicacionService {

    private final IInternoUbicacionRepository ubicacionRepository;
    private final IInternoRepository internoRepository;
    private final IRegistroInternoRepository registroRepository;
    private final IInternoUbicacionMapper ubicacionMapper;

    private static final Long TIPO_REGISTRO_CAMBIO_UBICACION = 3L; // Asumiendo ID en ms-core

    @Override
    @Transactional
    public InternoUbicacionResponseDTO asignarUbicacion(InternoUbicacionRequestDTO request) {
        log.info("Asignando ubicación a interno: {}", request.getIdInterno());

        // Validaciones
        if (request.getIdInterno() == null) {
            throw new BadRequestException("El ID del interno es obligatorio");
        }
        if (request.getIdInstitutoSede() == null) {
            throw new BadRequestException("El ID de la sede es obligatorio");
        }

        // Verificar que el interno existe y está activo
        var interno = internoRepository.findById(request.getIdInterno())
                .orElseThrow(() -> new ResourceNotFoundException("Interno no encontrado con ID: " + request.getIdInterno()));

        if (!interno.getEstado()) {
            throw new BadRequestException("No se puede asignar ubicación a un interno inactivo");
        }

        // Cerrar ubicación actual si existe
        ubicacionRepository.cerrarUbicacionActual(request.getIdInterno());

        // Crear nueva ubicación
        InternoUbicacion ubicacion = ubicacionMapper.toEntity(request);
        ubicacion.setFechaInicio(LocalDateTime.now());
        ubicacion.setEstadoActual(true);
        ubicacion = ubicacionRepository.save(ubicacion);

        log.info("Ubicación asignada con ID: {}", ubicacion.getIdUbicacion());
        return ubicacionMapper.toResponse(ubicacion);
    }

    @Override
    @Transactional
    public InternoUbicacionResponseDTO cambiarUbicacion(Long idInterno, Long nuevaSedeId, String observaciones) {
        log.info("Cambiando ubicación del interno {} a sede: {}", idInterno, nuevaSedeId);

        // Cerrar ubicación actual
        ubicacionRepository.cerrarUbicacionActual(idInterno);

        // Crear nueva ubicación
        InternoUbicacion nuevaUbicacion = new InternoUbicacion();
        nuevaUbicacion.setIdInterno(idInterno);
        nuevaUbicacion.setIdInstitutoSede(nuevaSedeId);
        nuevaUbicacion.setFechaInicio(LocalDateTime.now());
        nuevaUbicacion.setEstadoActual(true);
        nuevaUbicacion = ubicacionRepository.save(nuevaUbicacion);

        // Registrar en historial
        RegistroInterno registro = new RegistroInterno();
        registro.setIdInterno(idInterno);
        registro.setIdInstitutoSede(nuevaSedeId);
        registro.setTipoRegistroId(TIPO_REGISTRO_CAMBIO_UBICACION);
        registro.setFechaRegistro(LocalDateTime.now());
        registro.setObservaciones(observaciones);
        registroRepository.save(registro);

        log.info("Ubicación cambiada. Nueva ubicación ID: {}", nuevaUbicacion.getIdUbicacion());
        return ubicacionMapper.toResponse(nuevaUbicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public InternoUbicacionResponseDTO obtenerUbicacionActual(Long idInterno) {
        log.info("Obteniendo ubicación actual del interno: {}", idInterno);
        InternoUbicacion ubicacion = ubicacionRepository.findUbicacionActualByInterno(idInterno)
                .orElseThrow(() -> new ResourceNotFoundException("El interno no tiene ubicación actual asignada"));
        return ubicacionMapper.toResponse(ubicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InternoUbicacionResponseDTO> obtenerHistorialUbicaciones(Long idInterno) {
        log.info("Obteniendo historial de ubicaciones del interno: {}", idInterno);
        List<InternoUbicacion> ubicaciones = ubicacionRepository.findByIdInternoOrderByFechaInicioDesc(idInterno);
        return ubicacionMapper.toResponseList(ubicaciones);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InternoUbicacionResponseDTO> obtenerInternosEnSede(Long idSede) {
        log.info("Obteniendo internos en sede: {}", idSede);
        List<InternoUbicacion> ubicaciones = ubicacionRepository.findInternosEnSede(idSede);
        return ubicacionMapper.toResponseList(ubicaciones);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarInternosEnSede(Long idSede) {
        return ubicacionRepository.countInternosEnSede(idSede);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean estaEnSede(Long idInterno, Long idSede) {
        return obtenerUbicacionActual(idInterno).getIdInstitutoSede().equals(idSede);
    }
}