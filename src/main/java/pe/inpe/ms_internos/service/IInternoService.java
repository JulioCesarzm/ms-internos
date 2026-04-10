package pe.inpe.ms_internos.service;

import pe.inpe.ms_internos.dto.InternoRequestDTO;
import pe.inpe.ms_internos.dto.InternoResponseDTO;

import java.util.List;

public interface IInternoService {

    // CRUD básico
    InternoResponseDTO crear(InternoRequestDTO request);

    InternoResponseDTO actualizar(Long id, InternoRequestDTO request);

    InternoResponseDTO obtenerPorId(Long id);

    List<InternoResponseDTO> listarTodos();

    List<InternoResponseDTO> listarActivos();

    void desactivar(Long id);

    void activar(Long id);

    // Consultas específicas
    InternoResponseDTO obtenerPorCodigo(String codigoInterno);

    InternoResponseDTO obtenerPorIdPersona(Long idPersona);

    boolean existePorCodigo(String codigoInterno);

    boolean existePorIdPersona(Long idPersona);
}