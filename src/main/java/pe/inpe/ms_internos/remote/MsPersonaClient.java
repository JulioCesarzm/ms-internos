package pe.inpe.ms_internos.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.inpe.ms_internos.dto.*;

import java.util.List;

@FeignClient(name="ms-persona")
public interface MsPersonaClient {

    @GetMapping("/api/v1/persona/{id}")
    GenericResponseDTO<PersonaResponseDTO> obtenerPersona(@PathVariable("id") Long id);

    @GetMapping("/api/v1/persona/{id}/tiene-rol/{tipoRolId}")
    GenericResponseDTO<ValidacionRolDTO> tieneRolActivo(@PathVariable("id") Long id, @PathVariable("tipoRolId") Long tipoRolId);

    @GetMapping("/api/v1/persona/{id}/direcciones")
    GenericResponseDTO<List<PersonaDireccionResponseDTO>> obtenerDirecciones(@PathVariable("id") Long id);

    @PostMapping("/api/v1/persona/{id}/roles")
    GenericResponseDTO<PersonaRolResponseDTO> asignarRol(@PathVariable("id") Long id, @RequestBody PersonaRolRequestDTO request);
}
