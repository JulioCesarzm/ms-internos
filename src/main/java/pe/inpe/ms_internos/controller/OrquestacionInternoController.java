package pe.inpe.ms_internos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.inpe.ms_internos.dto.*;
import pe.inpe.ms_internos.service.IOrquestacionInternoService;

@Slf4j
@RestController
@RequestMapping("/api/v1/internos")
@RequiredArgsConstructor
public class OrquestacionInternoController {

    private final IOrquestacionInternoService orquestacionService;

    /**
     * Registra el ingreso completo de un nuevo interno al sistema penitenciario.
     * Flujo:
     * 1. Valida persona en ms-persona
     * 2. Valida sede en ms-instituciones
     * 3. Crea interno
     * 4. Asigna ubicación inicial
     * 5. Asigna clasificación (opcional)
     * 6. Registra ingreso en historial
     * 7. Asigna rol INTERNO en ms-persona
     * 8. Crea expediente judicial (opcional)
     */
    @PostMapping("/ingreso-completo")
    public ResponseEntity<GenericResponseDTO<InternoResponseDTO>> registrarIngresoCompleto(
            @Valid @RequestBody IngresoCompletoRequestDTO request) {

        log.info("POST /api/v1/internos/ingreso-completo - Persona: {}", request.getIdPersona());

        InternoResponseDTO response = orquestacionService.registrarIngresoCompleto(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponseDTO.<InternoResponseDTO>builder()
                        .response(response)
                        .build());
    }

    /**
     * Obtiene la ficha completa de un interno.
     * Incluye datos de:
     * - ms-persona (datos personales, direcciones)
     * - ms-interno (ubicación, clasificación, historial)
     * - ms-judicial (expedientes, mandato activo)
     */
    @GetMapping("/{id}/ficha-completa")
    public ResponseEntity<GenericResponseDTO<FichaCompletaResponseDTO>> obtenerFichaCompleta(
            @PathVariable("id") Long idInterno) {

        log.info("GET /api/v1/internos/{}/ficha-completa", idInterno);

        FichaCompletaResponseDTO response = orquestacionService.obtenerFichaCompleta(idInterno);

        return ResponseEntity.ok(GenericResponseDTO.<FichaCompletaResponseDTO>builder()
                .response(response)
                .build());
    }

    /**
     * Valida si un interno puede ser trasladado.
     */
    @GetMapping("/{id}/puede-trasladar")
    public ResponseEntity<GenericResponseDTO<ValidacionTrasladoDTO>> puedeSerTrasladado(
            @PathVariable("id") Long idInterno) {

        log.info("GET /api/v1/internos/{}/puede-trasladar", idInterno);

        boolean puede = orquestacionService.puedeSerTrasladado(idInterno);
        String motivo = puede ? null : orquestacionService.obtenerMotivoRestriccionTraslado(idInterno);

        ValidacionTrasladoDTO response = ValidacionTrasladoDTO.builder()
                .puedeSerTrasladado(puede)
                .motivoRestriccion(motivo)
                .build();

        return ResponseEntity.ok(GenericResponseDTO.<ValidacionTrasladoDTO>builder()
                .response(response)
                .build());
    }

    /**
     * Registra el egreso de un interno del sistema penitenciario.
     */
    @PostMapping("/{id}/egreso")
    public ResponseEntity<GenericResponseDTO<Void>> registrarEgreso(
            @PathVariable("id") Long idInterno,
            @RequestBody EgresoRequestDTO request) {

        log.info("POST /api/v1/internos/{}/egreso - Motivo: {}", idInterno, request.getMotivo());

        orquestacionService.registrarEgreso(idInterno, request.getMotivo());

        return ResponseEntity.ok(GenericResponseDTO.<Void>builder()
                .response(null)
                .build());
    }
}
