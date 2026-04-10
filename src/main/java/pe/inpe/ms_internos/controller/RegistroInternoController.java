package pe.inpe.ms_internos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.inpe.ms_internos.dto.GenericResponseDTO;
import pe.inpe.ms_internos.dto.RegistroInternoRequestDTO;
import pe.inpe.ms_internos.dto.RegistroInternoResponseDTO;
import pe.inpe.ms_internos.service.IRegistroInternoService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/internos")
@RequiredArgsConstructor
public class RegistroInternoController {

    private final IRegistroInternoService registroService;

    @PostMapping("/{id}/registros")
    public ResponseEntity<GenericResponseDTO<RegistroInternoResponseDTO>> registrar(
            @PathVariable("id") Long idInterno,
            @RequestBody RegistroInternoRequestDTO request) {
        request.setIdInterno(idInterno);
        RegistroInternoResponseDTO response = registroService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponseDTO.<RegistroInternoResponseDTO>builder()
                        .response(response)
                        .build());
    }

    @GetMapping("/{id}/registros")
    public ResponseEntity<GenericResponseDTO<List<RegistroInternoResponseDTO>>> obtenerPorInterno(
            @PathVariable("id") Long idInterno) {
        List<RegistroInternoResponseDTO> response = registroService.obtenerPorInterno(idInterno);
        return ResponseEntity.ok(GenericResponseDTO.<List<RegistroInternoResponseDTO>>builder()
                .response(response)
                .build());
    }

    @GetMapping("/{id}/registros/tipo/{tipoRegistroId}")
    public ResponseEntity<GenericResponseDTO<List<RegistroInternoResponseDTO>>> obtenerPorTipo(
            @PathVariable("id") Long idInterno,
            @PathVariable Long tipoRegistroId) {
        List<RegistroInternoResponseDTO> response = registroService.obtenerPorTipo(idInterno, tipoRegistroId);
        return ResponseEntity.ok(GenericResponseDTO.<List<RegistroInternoResponseDTO>>builder()
                .response(response)
                .build());
    }

    @GetMapping("/{id}/registros/ultimo")
    public ResponseEntity<GenericResponseDTO<RegistroInternoResponseDTO>> obtenerUltimoRegistro(
            @PathVariable("id") Long idInterno) {
        RegistroInternoResponseDTO response = registroService.obtenerUltimoRegistro(idInterno);
        return ResponseEntity.ok(GenericResponseDTO.<RegistroInternoResponseDTO>builder()
                .response(response)
                .build());
    }

    @GetMapping("/{id}/registros/rango")
    public ResponseEntity<GenericResponseDTO<List<RegistroInternoResponseDTO>>> obtenerPorRangoFechas(
            @PathVariable("id") Long idInterno,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<RegistroInternoResponseDTO> response = registroService.obtenerPorRangoFechas(idInterno, inicio, fin);
        return ResponseEntity.ok(GenericResponseDTO.<List<RegistroInternoResponseDTO>>builder()
                .response(response)
                .build());
    }
}