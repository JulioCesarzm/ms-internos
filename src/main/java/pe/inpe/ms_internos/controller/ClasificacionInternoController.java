package pe.inpe.ms_internos.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.inpe.ms_internos.dto.ClasificacionInternoRequestDTO;
import pe.inpe.ms_internos.dto.ClasificacionInternoResponseDTO;
import pe.inpe.ms_internos.dto.GenericResponseDTO;
import pe.inpe.ms_internos.service.IClasificacionInternoService;

import java.util.List;

@RestController
@RequestMapping("/internos")
@RequiredArgsConstructor
public class ClasificacionInternoController {

    private final IClasificacionInternoService clasificacionService;

    @PostMapping("/{id}/clasificacion")
    public ResponseEntity<GenericResponseDTO<ClasificacionInternoResponseDTO>> asignarClasificacion(
            @PathVariable("id") Long idInterno,
            @RequestBody ClasificacionInternoRequestDTO request) {
        request.setIdInterno(idInterno);
        ClasificacionInternoResponseDTO response = clasificacionService.asignarClasificacion(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponseDTO.<ClasificacionInternoResponseDTO>builder()
                        .response(response)
                        .build());
    }

    @PutMapping("/{id}/clasificacion/cambiar")
    public ResponseEntity<GenericResponseDTO<ClasificacionInternoResponseDTO>> cambiarClasificacion(
            @PathVariable("id") Long idInterno,
            @RequestParam Long nuevoNivelSeguridadId) {
        ClasificacionInternoResponseDTO response = clasificacionService.cambiarClasificacion(idInterno, nuevoNivelSeguridadId);
        return ResponseEntity.ok(GenericResponseDTO.<ClasificacionInternoResponseDTO>builder()
                .response(response)
                .build());
    }

    @GetMapping("/{id}/clasificacion/actual")
    public ResponseEntity<GenericResponseDTO<ClasificacionInternoResponseDTO>> obtenerClasificacionActiva(
            @PathVariable("id") Long idInterno) {
        ClasificacionInternoResponseDTO response = clasificacionService.obtenerClasificacionActiva(idInterno);
        return ResponseEntity.ok(GenericResponseDTO.<ClasificacionInternoResponseDTO>builder()
                .response(response)
                .build());
    }

    @GetMapping("/{id}/clasificacion/historial")
    public ResponseEntity<GenericResponseDTO<List<ClasificacionInternoResponseDTO>>> obtenerHistorialClasificaciones(
            @PathVariable("id") Long idInterno) {
        List<ClasificacionInternoResponseDTO> response = clasificacionService.obtenerHistorialClasificaciones(idInterno);
        return ResponseEntity.ok(GenericResponseDTO.<List<ClasificacionInternoResponseDTO>>builder()
                .response(response)
                .build());
    }

    @GetMapping("/clasificacion/nivel/{nivelSeguridadId}")
    public ResponseEntity<GenericResponseDTO<List<ClasificacionInternoResponseDTO>>> obtenerInternosPorNivelSeguridad(
            @PathVariable Long nivelSeguridadId) {
        List<ClasificacionInternoResponseDTO> response = clasificacionService.obtenerInternosPorNivelSeguridad(nivelSeguridadId);
        return ResponseEntity.ok(GenericResponseDTO.<List<ClasificacionInternoResponseDTO>>builder()
                .response(response)
                .build());
    }
}