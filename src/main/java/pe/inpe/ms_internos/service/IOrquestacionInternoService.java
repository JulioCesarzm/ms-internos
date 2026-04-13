package pe.inpe.ms_internos.service;

import pe.inpe.ms_internos.dto.FichaCompletaResponseDTO;
import pe.inpe.ms_internos.dto.IngresoCompletoRequestDTO;
import pe.inpe.ms_internos.dto.InternoResponseDTO;

public interface IOrquestacionInternoService {

    InternoResponseDTO registrarIngresoCompleto(IngresoCompletoRequestDTO request);


    FichaCompletaResponseDTO obtenerFichaCompleta(Long idInterno);


    boolean puedeSerTrasladado(Long idInterno);

    String obtenerMotivoRestriccionTraslado(Long idInterno);

    void registrarEgreso(Long idInterno, String motivo);
}