package pe.inpe.ms_internos.service;

import pe.inpe.ms_internos.dto.FichaCompletaResponseDTO;
import pe.inpe.ms_internos.dto.IngresoCompletoRequestDTO;
import pe.inpe.ms_internos.dto.InternoResponseDTO;

public interface IOrquestacionInternoService {

    /**
     * Registra un ingreso completo de interno.
     * Flujo:
     * 1. Valida que la persona existe en ms-persona
     * 2. Crea el interno
     * 3. Asigna ubicación inicial
     * 4. Asigna clasificación (opcional)
     * 5. Crea registro de ingreso
     * 6. (Opcional) Solicita crear expediente a ms-judicial
     */
    InternoResponseDTO registrarIngresoCompleto(IngresoCompletoRequestDTO request);

    /**
     * Obtiene la ficha completa del interno.
     * Agrega datos de:
     * - ms-persona (datos personales)
     * - ms-judicial (expedientes, mandato activo, delitos)
     * - ms-interno (ubicación, clasificación, registros)
     */
    FichaCompletaResponseDTO obtenerFichaCompleta(Long idInterno);

    /**
     * Valida si un interno puede ser trasladado.
     * Reglas:
     * - Debe estar activo
     * - No tener restricciones judiciales graves
     * - Ubicación actual válida
     */
    boolean puedeSerTrasladado(Long idInterno);

    /**
     * Obtiene el motivo por el cual NO puede ser trasladado.
     */
    String obtenerMotivoRestriccionTraslado(Long idInterno);

    /**
     * Egreso de interno (baja del sistema).
     * Flujo:
     * 1. Cambia estado a inactivo
     * 2. Cierra ubicación actual
     * 3. Cierra clasificación activa
     * 4. Registra egreso en historial
     */
    void registrarEgreso(Long idInterno, String motivo);
}