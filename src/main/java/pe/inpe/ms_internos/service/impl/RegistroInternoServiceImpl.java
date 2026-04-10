package pe.inpe.ms_internos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.inpe.ms_internos.dto.DetalleRegistroRequestDTO;
import pe.inpe.ms_internos.dto.RegistroInternoRequestDTO;
import pe.inpe.ms_internos.dto.RegistroInternoResponseDTO;
import pe.inpe.ms_internos.entity.DetalleRegistro;
import pe.inpe.ms_internos.entity.RegistroInterno;
import pe.inpe.ms_internos.exception.BadRequestException;
import pe.inpe.ms_internos.exception.ResourceNotFoundException;
import pe.inpe.ms_internos.mapper.IDetalleRegistroMapper;
import pe.inpe.ms_internos.mapper.IRegistroInternoMapper;
import pe.inpe.ms_internos.repository.IDetalleRegistroRepository;
import pe.inpe.ms_internos.repository.IInternoRepository;
import pe.inpe.ms_internos.repository.IRegistroInternoRepository;
import pe.inpe.ms_internos.service.IRegistroInternoService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroInternoServiceImpl implements IRegistroInternoService {

    private final IRegistroInternoRepository registroRepository;
    private final IDetalleRegistroRepository detalleRepository;
    private final IInternoRepository internoRepository;
    private final IRegistroInternoMapper registroMapper;
    private final IDetalleRegistroMapper detalleMapper;

    @Override
    @Transactional
    public RegistroInternoResponseDTO registrar(RegistroInternoRequestDTO request) {
        log.info("Registrando movimiento para interno: {}", request.getIdInterno());

        // Validaciones
        if (request.getIdInterno() == null) {
            throw new BadRequestException("El ID del interno es obligatorio");
        }
        if (request.getTipoRegistroId() == null) {
            throw new BadRequestException("El tipo de registro es obligatorio");
        }

        // Verificar que el interno existe
        if (!internoRepository.existsById(request.getIdInterno())) {
            throw new ResourceNotFoundException("Interno no encontrado con ID: " + request.getIdInterno());
        }

        // Crear registro
        RegistroInterno registro = registroMapper.toEntity(request);
        registro.setFechaRegistro(LocalDateTime.now());
        registro = registroRepository.save(registro);
        log.info("Registro creado con ID: {}", registro.getIdRegistro());

        // Guardar detalles si existen
        if (request.getDetalles() != null && !request.getDetalles().isEmpty()) {
            List<DetalleRegistro> detalles = new ArrayList<>();
            for (DetalleRegistroRequestDTO detalleRequest : request.getDetalles()) {
                DetalleRegistro detalle = detalleMapper.toEntity(detalleRequest);
                detalle.setIdRegistro(registro.getIdRegistro());
                detalles.add(detalle);
            }
            detalleRepository.saveAll(detalles);
            log.info("{} detalles guardados para el registro", detalles.size());
        }

        // Recuperar con detalles
        return obtenerPorId(registro.getIdRegistro());
    }

    @Transactional(readOnly = true)
    private RegistroInternoResponseDTO obtenerPorId(Long id) {
        RegistroInterno registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado"));

        RegistroInternoResponseDTO response = registroMapper.toResponse(registro);
        response.setDetalles(detalleMapper.toResponseList(detalleRepository.findByIdRegistro(id)));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroInternoResponseDTO> obtenerPorInterno(Long idInterno) {
        log.info("Obteniendo registros del interno: {}", idInterno);

        if (!internoRepository.existsById(idInterno)) {
            throw new ResourceNotFoundException("Interno no encontrado con ID: " + idInterno);
        }

        List<RegistroInterno> registros = registroRepository.findByIdInternoOrderByFechaRegistroDesc(idInterno);
        return registros.stream()
                .map(this::enriquecerConDetalles)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroInternoResponseDTO> obtenerPorTipo(Long idInterno, Long tipoRegistroId) {
        log.info("Obteniendo registros del interno {} con tipo: {}", idInterno, tipoRegistroId);
        List<RegistroInterno> registros = registroRepository.findByIdInternoAndTipoRegistroId(idInterno, tipoRegistroId);
        return registros.stream()
                .map(this::enriquecerConDetalles)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RegistroInternoResponseDTO obtenerUltimoRegistro(Long idInterno) {
        log.info("Obteniendo último registro del interno: {}", idInterno);
        RegistroInterno registro = registroRepository.findUltimoRegistroByInterno(idInterno)
                .orElseThrow(() -> new ResourceNotFoundException("No hay registros para el interno: " + idInterno));
        return enriquecerConDetalles(registro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroInternoResponseDTO> obtenerPorRangoFechas(Long idInterno, LocalDateTime inicio, LocalDateTime fin) {
        log.info("Obteniendo registros del interno {} entre {} y {}", idInterno, inicio, fin);
        List<RegistroInterno> registros = registroRepository.findByInternoAndRangoFechas(idInterno, inicio, fin);
        return registros.stream()
                .map(this::enriquecerConDetalles)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroInternoResponseDTO> obtenerIngresosRecientes(Long tipoRegistroId, LocalDateTime desde) {
        log.info("Obteniendo ingresos recientes de tipo: {}", tipoRegistroId);
        List<RegistroInterno> registros = registroRepository.findByTipoRegistroIdAndFechaRegistroAfter(tipoRegistroId, desde);
        return registros.stream()
                .map(this::enriquecerConDetalles)
                .toList();
    }

    private RegistroInternoResponseDTO enriquecerConDetalles(RegistroInterno registro) {
        RegistroInternoResponseDTO response = registroMapper.toResponse(registro);
        response.setDetalles(detalleMapper.toResponseList(detalleRepository.findByIdRegistro(registro.getIdRegistro())));
        return response;
    }
}