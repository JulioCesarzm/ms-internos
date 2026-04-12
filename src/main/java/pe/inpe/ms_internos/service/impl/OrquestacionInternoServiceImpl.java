package pe.inpe.ms_internos.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.inpe.ms_internos.dto.*;
import pe.inpe.ms_internos.entity.Interno;
import pe.inpe.ms_internos.exception.ResourceNotFoundException;
import pe.inpe.ms_internos.mapper.Client.IExpedienteClientMapper;
import pe.inpe.ms_internos.mapper.Client.IPersonaClientMapper;
import pe.inpe.ms_internos.repository.IInternoRepository;
import pe.inpe.ms_internos.service.*;
import pe.inpe.ms_internos.remote.MsInstitucionesClient;
import pe.inpe.ms_internos.remote.MsJudicialClient;
import pe.inpe.ms_internos.remote.MsPersonaClient;
import pe.inpe.ms_internos.util.GeneralConstants.RolPersonaConstants;
import pe.inpe.ms_internos.util.GeneralConstants.TipoRegistroConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrquestacionInternoServiceImpl implements IOrquestacionInternoService {

    private final IInternoService internoService;
    private final IInternoUbicacionService ubicacionService;
    private final IClasificacionInternoService clasificacionService;
    private final IRegistroInternoService registroService;
    private final IInternoRepository internoRepository;

    private final MsPersonaClient personaClient;
    private final MsInstitucionesClient sedeClient;
    private final MsJudicialClient judicialClient;

    private final IPersonaClientMapper personaClientMapper;
    private final IExpedienteClientMapper expedienteClientMapper;

    @Override
    @Transactional
    public InternoResponseDTO registrarIngresoCompleto(IngresoCompletoRequestDTO request) {
        log.info("Iniciando registro de ingreso completo para persona: {}", request.getIdPersona());

        // 1. Validar que la persona existe
        try {
            PersonaResponseDTO personaResponse = personaClient.obtenerPersona(request.getIdPersona()).getResponse();
            PersonaResumenDTO persona = personaClientMapper.toResumenDTO(personaResponse);
            log.info("Persona encontrada: {} - {}", persona.getNombreCompleto(), persona.getNumeroDocumento());
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Persona no encontrada con ID: " + request.getIdPersona());
        }

        // 2. Validar que la sede existe
        try {
            SedeResponseDTO sede = sedeClient.obtenerSede(request.getIdInstitutoSede()).getResponse();
            log.info("Sede encontrada: {}", sede.getNombre());
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Sede no encontrada con ID: " + request.getIdInstitutoSede());
        }

        // 3. Crear interno
        InternoRequestDTO internoRequest = InternoRequestDTO.builder()
                .idPersona(request.getIdPersona())
                .codigoInterno(request.getCodigoInterno())
                .fechaIngreso(request.getFechaIngreso() != null ? request.getFechaIngreso() : LocalDate.now())
                .build();
        InternoResponseDTO interno = internoService.crear(internoRequest);
        log.info("Interno creado con ID: {}", interno.getIdInterno());

        // 4. Asignar ubicación inicial
        InternoUbicacionRequestDTO ubicacionRequest = InternoUbicacionRequestDTO.builder()
                .idInterno(interno.getIdInterno())
                .idInstitutoSede(request.getIdInstitutoSede())
                .build();
        ubicacionService.asignarUbicacion(ubicacionRequest);
        log.info("Ubicación inicial asignada: sede {}", request.getIdInstitutoSede());

        // 5. Asignar clasificación (opcional)
        if (request.getNivelSeguridadId() != null) {
            ClasificacionInternoRequestDTO clasificacionRequest = ClasificacionInternoRequestDTO.builder()
                    .idInterno(interno.getIdInterno())
                    .nivelSeguridadId(request.getNivelSeguridadId())
                    .fechaInicio(LocalDate.now())
                    .build();
            clasificacionService.asignarClasificacion(clasificacionRequest);
            log.info("Clasificación asignada: nivel {}", request.getNivelSeguridadId());
        }

        // 6. Registrar ingreso en historial
        RegistroInternoRequestDTO registroRequest = RegistroInternoRequestDTO.builder()
                .idInterno(interno.getIdInterno())
                .idInstitutoSede(request.getIdInstitutoSede())
                .tipoRegistroId(TipoRegistroConstants.INGRESO)
                .observaciones(request.getObservaciones())
                .build();
        registroService.registrar(registroRequest);
        log.info("Registro de ingreso creado");

        // 7. Asignar rol INTERNO a la persona
        try {
            PersonaRolRequestDTO rolRequest = PersonaRolRequestDTO.builder()
                    .tipoPersonaRolId(RolPersonaConstants.INTERNO)
                    .fechaInicio(LocalDate.now())
                    .observaciones("Rol asignado por ingreso al sistema penitenciario")
                    .build();
            personaClient.asignarRol(request.getIdPersona(), rolRequest);
            log.info("Rol INTERNO asignado a persona {}", request.getIdPersona());
        } catch (Exception e) {
            log.warn("No se pudo asignar rol INTERNO: {}", e.getMessage());
        }

        // 8. (Opcional) Crear expediente judicial
        if (request.getCrearExpediente() != null && request.getCrearExpediente()) {
            ExpedienteRequestDTO expRequest = ExpedienteRequestDTO.builder()
                    .idInterno(interno.getIdInterno())
                    .estadoProcesalId(request.getEstadoProcesalId())
                    .fechaInicio(LocalDate.now())
                    .observaciones("Expediente creado por ingreso")
                    .descripcionHistorialInicial("Ingreso al sistema penitenciario")
                    .delitosIds(request.getDelitosIds())
                    .build();

            try {
                judicialClient.crearExpediente(expRequest);
                log.info("Expediente judicial creado para interno {}", interno.getIdInterno());
            } catch (Exception e) {
                log.error("Error al crear expediente judicial: {}", e.getMessage());
            }
        }

        return interno;
    }

    @Override
    @Transactional(readOnly = true)
    public FichaCompletaResponseDTO obtenerFichaCompleta(Long idInterno) {
        log.info("Obteniendo ficha completa del interno: {}", idInterno);

        // 1. Datos básicos del interno
        InternoResponseDTO interno = internoService.obtenerPorId(idInterno);

        // 2. Datos personales (ms-persona)
        PersonaResumenDTO persona = null;
        try {
            PersonaResponseDTO personaResponse = personaClient.obtenerPersona(interno.getIdPersona()).getResponse();
            log.info("Persona obtenida: {} {}", personaResponse.getNombres(), personaResponse.getApellidoPaterno());
            persona = personaClientMapper.toResumenDTO(personaResponse);
        } catch (Exception e) {
            log.warn("No se pudo obtener datos de persona: {}", e.getMessage());
            persona = PersonaResumenDTO.builder()
                    .idPersona(interno.getIdPersona())
                    .nombreCompleto("No disponible")
                    .build();
        }

        // 3. Ubicación actual
        InternoUbicacionResponseDTO ubicacionActual = null;
        try {
            ubicacionActual = ubicacionService.obtenerUbicacionActual(idInterno);
        } catch (Exception e) {
            log.warn("No se pudo obtener ubicación actual: {}", e.getMessage());
        }

        // 4. Clasificación actual
        ClasificacionInternoResponseDTO clasificacionActual = null;
        try {
            clasificacionActual = clasificacionService.obtenerClasificacionActiva(idInterno);
        } catch (Exception e) {
            log.warn("No se pudo obtener clasificación: {}", e.getMessage());
        }

        // 5. Últimos registros
        List<RegistroInternoResponseDTO> ultimosRegistros = registroService.obtenerPorInterno(idInterno);

        // 6. Resumen judicial (ms-judicial)
        ResumenJudicialDTO resumenJudicial = obtenerResumenJudicial(idInterno);

        // 7. Validar si puede ser trasladado
        boolean puedeTrasladar = puedeSerTrasladado(idInterno);
        String motivo = puedeTrasladar ? null : obtenerMotivoRestriccionTraslado(idInterno);

        return FichaCompletaResponseDTO.builder()
                .idInterno(interno.getIdInterno())
                .codigoInterno(interno.getCodigoInterno())
                .fechaIngreso(interno.getFechaIngreso())
                .estado(interno.getEstado())
                .persona(persona)
                .ubicacionActual(ubicacionActual)
                .clasificacionActual(clasificacionActual)
                .ultimosRegistros(ultimosRegistros.size() > 5 ? ultimosRegistros.subList(0, 5) : ultimosRegistros)
                .resumenJudicial(resumenJudicial)
                .puedeSerTrasladado(puedeTrasladar)
                .motivoRestriccion(motivo)
                .fechaConsulta(LocalDateTime.now())
                .build();
    }

    private ResumenJudicialDTO obtenerResumenJudicial(Long idInterno) {
        try {
            List<ExpedienteResponseDTO> expedientes = judicialClient.listarExpedientesPorInterno(idInterno).getResponse();
            MandatoActivoDTO mandato = null;
            try {
                mandato = judicialClient.obtenerMandatoActivo(idInterno).getResponse();
            } catch (Exception e) {
                log.debug("Interno {} no tiene mandato activo", idInterno);
            }
            return expedienteClientMapper.toResumenJudicial(expedientes, mandato);
        } catch (Exception e) {
            log.warn("No se pudo obtener resumen judicial: {}", e.getMessage());
            return expedienteClientMapper.emptyResumenJudicial();
        }
    }

    @Override
    public boolean puedeSerTrasladado(Long idInterno) {
        Interno interno = internoRepository.findById(idInterno)
                .orElseThrow(() -> new ResourceNotFoundException("Interno no encontrado"));
        return interno.getEstado();
    }

    @Override
    public String obtenerMotivoRestriccionTraslado(Long idInterno) {
        Interno interno = internoRepository.findById(idInterno)
                .orElseThrow(() -> new ResourceNotFoundException("Interno no encontrado"));
        if (!interno.getEstado()) {
            return "El interno no está activo en el sistema";
        }
        return null;
    }

    @Override
    @Transactional
    public void registrarEgreso(Long idInterno, String motivo) {
        log.info("Registrando egreso del interno: {}", idInterno);

        internoService.desactivar(idInterno);

        InternoUbicacionResponseDTO ubicacionActual = ubicacionService.obtenerUbicacionActual(idInterno);

        RegistroInternoRequestDTO registroRequest = RegistroInternoRequestDTO.builder()
                .idInterno(idInterno)
                .idInstitutoSede(ubicacionActual.getIdInstitutoSede())
                .tipoRegistroId(TipoRegistroConstants.EGRESO)
                .observaciones("Egreso del sistema. Motivo: " + motivo)
                .build();
        registroService.registrar(registroRequest);

        log.info("Egreso registrado para interno: {}", idInterno);
    }
}