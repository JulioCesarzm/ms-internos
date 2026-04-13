package pe.inpe.ms_internos.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.inpe.ms_internos.dto.ExpedienteRequestDTO;
import pe.inpe.ms_internos.dto.ExpedienteResponseDTO;
import pe.inpe.ms_internos.dto.GenericResponseDTO;
import pe.inpe.ms_internos.dto.MandatoActivoDTO;

import java.util.List;

@FeignClient(name = "ms-judicial")
public interface MsJudicialClient {

    @PostMapping("/api/v1/expedientes")
    GenericResponseDTO<ExpedienteResponseDTO> crearExpediente(@RequestBody ExpedienteRequestDTO request);

    @GetMapping("/api/v1/expedientes/interno/{idInterno}")
    GenericResponseDTO<List<ExpedienteResponseDTO>> listarExpedientesPorInterno(@PathVariable("idInterno") Long idInterno);

    @GetMapping("/api/v1/mandatos/interno/{idInterno}/activo")
    GenericResponseDTO<MandatoActivoDTO> obtenerMandatoActivo(@PathVariable("idInterno") Long idInterno);
}
