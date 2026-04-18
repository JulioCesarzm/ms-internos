package pe.inpe.ms_internos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.inpe.ms_internos.dto.GenericResponseDTO;
import pe.inpe.ms_internos.dto.InternoRequestDTO;
import pe.inpe.ms_internos.dto.InternoResponseDTO;
import pe.inpe.ms_internos.service.IInternoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/internos")
@RequiredArgsConstructor
public class InternoController {

    private final IInternoService internoService;

    @PostMapping
    public ResponseEntity<GenericResponseDTO<InternoResponseDTO>> crear(
            @RequestBody InternoRequestDTO request) {
        InternoResponseDTO response = internoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponseDTO.<InternoResponseDTO>builder()
                        .response(response)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponseDTO<InternoResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody InternoRequestDTO request) {
        InternoResponseDTO response = internoService.actualizar(id, request);
        return ResponseEntity.ok(GenericResponseDTO.<InternoResponseDTO>builder()
                .response(response)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponseDTO<InternoResponseDTO>> obtenerPorId(
            @PathVariable Long id) {
        InternoResponseDTO response = internoService.obtenerPorId(id);
        return ResponseEntity.ok(GenericResponseDTO.<InternoResponseDTO>builder()
                .response(response)
                .build());
    }

    @GetMapping
    public ResponseEntity<GenericResponseDTO<List<InternoResponseDTO>>> listarTodos() {
        List<InternoResponseDTO> response = internoService.listarTodos();
        return ResponseEntity.ok(GenericResponseDTO.<List<InternoResponseDTO>>builder()
                .response(response)
                .build());
    }

    @GetMapping("/activos")
    public ResponseEntity<GenericResponseDTO<List<InternoResponseDTO>>> listarActivos() {
        List<InternoResponseDTO> response = internoService.listarActivos();
        return ResponseEntity.ok(GenericResponseDTO.<List<InternoResponseDTO>>builder()
                .response(response)
                .build());
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<GenericResponseDTO<Void>> desactivar(
            @PathVariable Long id) {
        internoService.desactivar(id);
        return ResponseEntity.ok(GenericResponseDTO.<Void>builder()
                .response(null)
                .build());
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<GenericResponseDTO<Void>> activar(
            @PathVariable Long id) {
        internoService.activar(id);
        return ResponseEntity.ok(GenericResponseDTO.<Void>builder()
                .response(null)
                .build());
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<GenericResponseDTO<InternoResponseDTO>> obtenerPorCodigo(
            @PathVariable String codigo) {
        InternoResponseDTO response = internoService.obtenerPorCodigo(codigo);
        return ResponseEntity.ok(GenericResponseDTO.<InternoResponseDTO>builder()
                .response(response)
                .build());
    }

    @GetMapping("/persona/{idPersona}")
    public ResponseEntity<GenericResponseDTO<InternoResponseDTO>> obtenerPorIdPersona(
            @PathVariable Long idPersona) {
        InternoResponseDTO response = internoService.obtenerPorIdPersona(idPersona);
        return ResponseEntity.ok(GenericResponseDTO.<InternoResponseDTO>builder()
                .response(response)
                .build());
    }

    @GetMapping("/validar/codigo/{codigo}")
    public ResponseEntity<GenericResponseDTO<Boolean>> existePorCodigo(
            @PathVariable String codigo) {
        boolean existe = internoService.existePorCodigo(codigo);
        return ResponseEntity.ok(GenericResponseDTO.<Boolean>builder()
                .response(existe)
                .build());
    }

    @GetMapping("/validar/persona/{idPersona}")
    public ResponseEntity<GenericResponseDTO<Boolean>> existePorIdPersona(
            @PathVariable Long idPersona) {
        boolean existe = internoService.existePorIdPersona(idPersona);
        return ResponseEntity.ok(GenericResponseDTO.<Boolean>builder()
                .response(existe)
                .build());
    }
}