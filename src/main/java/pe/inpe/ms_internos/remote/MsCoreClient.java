package pe.inpe.ms_internos.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.inpe.ms_internos.dto.GenericResponseDTO;
import pe.inpe.ms_internos.dto.ParameterTableResponseDTO;

@FeignClient(name = "ms-core", url = "http://localhost:8082")
public interface MsCoreClient {

    /**
     * Obtiene un parámetro por tipo y código
     */
    @GetMapping("/api/v1/core/parameters/tipo/{tipo}/codigo/{codigo}")
    GenericResponseDTO<ParameterTableResponseDTO> obtenerParametro(@PathVariable("tipo") Integer tipo, @PathVariable("codigo") String codigo);

    /**
     * Valida existencia de un parámetro por tipo y código
     */
    @GetMapping("/api/v1/core/parameters/tipo/{tipo}/codigo/{codigo}/existe")
    GenericResponseDTO<Boolean> existeParametro(@PathVariable("tipo") Integer tipo, @PathVariable("codigo") String codigo);

    /**
     * Obtiene un parámetro por ID
     */
    @GetMapping("/api/v1/core/parameters/{id}")
    GenericResponseDTO<ParameterTableResponseDTO> obtenerParametroPorId(@PathVariable("id") Long id);
}
