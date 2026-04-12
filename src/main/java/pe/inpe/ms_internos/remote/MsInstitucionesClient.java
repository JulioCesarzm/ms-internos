package pe.inpe.ms_internos.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.inpe.ms_internos.dto.GenericResponseDTO;
import pe.inpe.ms_internos.dto.SedeDisponibilidadDTO;
import pe.inpe.ms_internos.dto.SedeResponseDTO;

@FeignClient(name = "ms-Infraestructura-penitenciaria", url="http://localhost:8092")
public interface MsInstitucionesClient {

    /**
     * Obtiene una sede por ID (útil para validar existencia y obtener datos)
     */
    @GetMapping("/api/v1/sedes/{id}")
    GenericResponseDTO<SedeResponseDTO> obtenerSede(@PathVariable("id") Long id);

    /**
     * Obtiene disponibilidad de una sede
     */
    @GetMapping("/api/v1/sedes/disponibilidad/{id}")
    GenericResponseDTO<SedeDisponibilidadDTO> obtenerDisponibilidad(@PathVariable("id") Long id);
}
