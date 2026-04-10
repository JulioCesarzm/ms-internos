package pe.inpe.ms_internos.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.inpe.ms_internos.entity.RegistroInterno;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IRegistroInternoRepository extends JpaRepository<RegistroInterno, Long> {

    List<RegistroInterno> findByIdInternoOrderByFechaRegistroDesc(Long idInterno);

    List<RegistroInterno> findByIdInternoAndTipoRegistroId(Long idInterno, Long tipoRegistroId);

    List<RegistroInterno> findByIdInstitutoSede(Long idInstitutoSede);

    @Query("SELECT r FROM RegistroInterno r WHERE r.idInterno = :idInterno AND r.fechaRegistro BETWEEN :fechaInicio AND :fechaFin")
    List<RegistroInterno> findByInternoAndRangoFechas(
            @Param("idInterno") Long idInterno,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT r FROM RegistroInterno r WHERE r.idInterno = :idInterno ORDER BY r.fechaRegistro DESC LIMIT 1")
    Optional<RegistroInterno> findUltimoRegistroByInterno(@Param("idInterno") Long idInterno);

    // Para búsqueda de ingresos recientes
    List<RegistroInterno> findByTipoRegistroIdAndFechaRegistroAfter(Long tipoRegistroId, LocalDateTime fecha);
}
