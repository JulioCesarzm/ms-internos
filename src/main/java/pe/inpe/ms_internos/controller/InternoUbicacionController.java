package pe.inpe.ms_internos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.inpe.ms_internos.dto.GenericResponseDTO;
import pe.inpe.ms_internos.dto.InternoUbicacionRequestDTO;
import pe.inpe.ms_internos.dto.InternoUbicacionResponseDTO;
import pe.inpe.ms_internos.service.IInternoUbicacionService;

import java.util.List;

@RestController
@RequestMapping("/internos")
@RequiredArgsConstructor
public class InternoUbicacionController {

    private final IInternoUbicacionService ubicacionService;

    @PostMapping("/{id}/ubicacion")
    public ResponseEntity<GenericResponseDTO<InternoUbicacionResponseDTO>> asignarUbicacion(
            @PathVariable("id") Long idInterno,
            @RequestBody InternoUbicacionRequestDTO request) {
        request.setIdInterno(idInterno);
        InternoUbicacionResponseDTO response = ubicacionService.asignarUbicacion(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponseDTO.<InternoUbicacionResponseDTO>builder()
                        .response(response)
                        .build());
    }

    @PutMapping("/{id}/ubicacion/cambiar")
    public ResponseEntity<GenericResponseDTO<InternoUbicacionResponseDTO>> cambiarUbicacion(
            @PathVariable("id") Long idInterno,
            @RequestParam Long nuevaSedeId,
            @RequestParam(required = false) String observaciones) {
        InternoUbicacionResponseDTO response = ubicacionService.cambiarUbicacion(idInterno, nuevaSedeId, observaciones);
        return ResponseEntity.ok(GenericResponseDTO.<InternoUbicacionResponseDTO>builder()
                .response(response)
                .build());
    }

    @GetMapping("/{id}/ubicacion/actual")
    public ResponseEntity<GenericResponseDTO<InternoUbicacionResponseDTO>> obtenerUbicacionActual(
            @PathVariable("id") Long idInterno) {
        InternoUbicacionResponseDTO response = ubicacionService.obtenerUbicacionActual(idInterno);
        return ResponseEntity.ok(GenericResponseDTO.<InternoUbicacionResponseDTO>builder()
                .response(response)
                .build());
    }

    @GetMapping("/{id}/ubicacion/historial")
    public ResponseEntity<GenericResponseDTO<List<InternoUbicacionResponseDTO>>> obtenerHistorialUbicaciones(
            @PathVariable("id") Long idInterno) {
        List<InternoUbicacionResponseDTO> response = ubicacionService.obtenerHistorialUbicaciones(idInterno);
        return ResponseEntity.ok(GenericResponseDTO.<List<InternoUbicacionResponseDTO>>builder()
                .response(response)
                .build());
    }

    @GetMapping("/sedes/{idSede}/internos")
    public ResponseEntity<GenericResponseDTO<List<InternoUbicacionResponseDTO>>> obtenerInternosEnSede(
            @PathVariable Long idSede) {
        List<InternoUbicacionResponseDTO> response = ubicacionService.obtenerInternosEnSede(idSede);
        return ResponseEntity.ok(GenericResponseDTO.<List<InternoUbicacionResponseDTO>>builder()
                .response(response)
                .build());
    }

    @GetMapping("/sedes/{idSede}/internos/count")
    public ResponseEntity<GenericResponseDTO<Long>> contarInternosEnSede(
            @PathVariable Long idSede) {
        Long count = ubicacionService.contarInternosEnSede(idSede);
        return ResponseEntity.ok(GenericResponseDTO.<Long>builder()
                .response(count)
                .build());
    }

    @GetMapping("/{id}/ubicacion/validar-sede/{idSede}")
    public ResponseEntity<GenericResponseDTO<Boolean>> estaEnSede(
            @PathVariable("id") Long idInterno,
            @PathVariable Long idSede) {
        boolean esta = ubicacionService.estaEnSede(idInterno, idSede);
        return ResponseEntity.ok(GenericResponseDTO.<Boolean>builder()
                .response(esta)
                .build());
    }
}