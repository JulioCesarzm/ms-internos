package pe.inpe.ms_internos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.inpe.ms_internos.dto.InternoRequestDTO;
import pe.inpe.ms_internos.dto.InternoResponseDTO;
import pe.inpe.ms_internos.entity.Interno;
import pe.inpe.ms_internos.exception.BadRequestException;
import pe.inpe.ms_internos.exception.ResourceNotFoundException;
import pe.inpe.ms_internos.mapper.IInternoMapper;
import pe.inpe.ms_internos.repository.IInternoRepository;
import pe.inpe.ms_internos.service.IInternoService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternoServiceImpl implements IInternoService {

    private final IInternoRepository internoRepository;
    private final IInternoMapper internoMapper;

    @Override
    @Transactional
    public InternoResponseDTO crear(InternoRequestDTO request) {
        log.info("Creando nuevo interno para persona: {}", request.getIdPersona());

        // Validaciones
        if (request.getIdPersona() == null) {
            throw new BadRequestException("El ID de persona es obligatorio");
        }
        if (request.getCodigoInterno() == null || request.getCodigoInterno().trim().isEmpty()) {
            throw new BadRequestException("El código de interno es obligatorio");
        }

        // Verificar duplicados
        if (internoRepository.existsByCodigoInterno(request.getCodigoInterno())) {
            throw new BadRequestException("Ya existe un interno con el código: " + request.getCodigoInterno());
        }
        if (internoRepository.existsByIdPersona(request.getIdPersona())) {
            throw new BadRequestException("La persona ya está registrada como interno");
        }

        Interno interno = internoMapper.toEntity(request);
        interno.setEstado(true); // Por defecto activo
        interno = internoRepository.save(interno);

        log.info("Interno creado con ID: {} y código: {}", interno.getIdInterno(), interno.getCodigoInterno());
        return internoMapper.toResponse(interno);
    }

    @Override
    @Transactional
    public InternoResponseDTO actualizar(Long id, InternoRequestDTO request) {
        log.info("Actualizando interno con ID: {}", id);

        Interno interno = internoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interno no encontrado con ID: " + id));

        // Validar código único si cambió
        if (request.getCodigoInterno() != null
                && !request.getCodigoInterno().equals(interno.getCodigoInterno())
                && internoRepository.existsByCodigoInterno(request.getCodigoInterno())) {
            throw new BadRequestException("Ya existe un interno con el código: " + request.getCodigoInterno());
        }

        internoMapper.updateEntity(interno, request);
        interno = internoRepository.save(interno);

        log.info("Interno actualizado con ID: {}", interno.getIdInterno());
        return internoMapper.toResponse(interno);
    }

    @Override
    @Transactional(readOnly = true)
    public InternoResponseDTO obtenerPorId(Long id) {
        log.info("Buscando interno por ID: {}", id);
        Interno interno = internoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interno no encontrado con ID: " + id));
        return internoMapper.toResponse(interno);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InternoResponseDTO> listarTodos() {
        log.info("Listando todos los internos");
        List<Interno> internos = internoRepository.findAll();
        return internoMapper.toResponseList(internos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InternoResponseDTO> listarActivos() {
        log.info("Listando internos activos");
        List<Interno> internos = internoRepository.findAllActivos();
        return internoMapper.toResponseList(internos);
    }

    @Override
    @Transactional
    public void desactivar(Long id) {
        log.info("Desactivando interno con ID: {}", id);
        Interno interno = internoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interno no encontrado con ID: " + id));

        if (!interno.getEstado()) {
            throw new BadRequestException("El interno ya está inactivo");
        }

        interno.setEstado(false);
        internoRepository.save(interno);
        log.info("Interno desactivado con ID: {}", id);
    }

    @Override
    @Transactional
    public void activar(Long id) {
        log.info("Activando interno con ID: {}", id);
        Interno interno = internoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interno no encontrado con ID: " + id));

        if (interno.getEstado()) {
            throw new BadRequestException("El interno ya está activo");
        }

        interno.setEstado(true);
        internoRepository.save(interno);
        log.info("Interno activado con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public InternoResponseDTO obtenerPorCodigo(String codigoInterno) {
        log.info("Buscando interno por código: {}", codigoInterno);
        Interno interno = internoRepository.findByCodigoInterno(codigoInterno)
                .orElseThrow(() -> new ResourceNotFoundException("Interno no encontrado con código: " + codigoInterno));
        return internoMapper.toResponse(interno);
    }

    @Override
    @Transactional(readOnly = true)
    public InternoResponseDTO obtenerPorIdPersona(Long idPersona) {
        log.info("Buscando interno por persona ID: {}", idPersona);
        Interno interno = internoRepository.findByIdPersona(idPersona)
                .orElseThrow(() -> new ResourceNotFoundException("Interno no encontrado para persona ID: " + idPersona));
        return internoMapper.toResponse(interno);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(String codigoInterno) {
        return internoRepository.existsByCodigoInterno(codigoInterno);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorIdPersona(Long idPersona) {
        return internoRepository.existsByIdPersona(idPersona);
    }
}