package pe.inpe.ms_internos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.inpe.ms_internos.entity.InternoUbicacion;

import java.util.List;
import java.util.Optional;

@Repository
public interface IInternoUbicacionRepository extends JpaRepository<InternoUbicacion, Long> {

    List<InternoUbicacion> findByIdInternoOrderByFechaInicioDesc(Long idInterno);

    @Query("SELECT u FROM InternoUbicacion u WHERE u.idInterno = :idInterno AND u.estadoActual = true")
    Optional<InternoUbicacion> findUbicacionActualByInterno(@Param("idInterno") Long idInterno);

    @Query("SELECT u FROM InternoUbicacion u WHERE u.idInstitutoSede = :idSede AND u.estadoActual = true")
    List<InternoUbicacion> findInternosEnSede(@Param("idSede") Long idSede);

    @Modifying
    @Query("UPDATE InternoUbicacion u SET u.estadoActual = false, u.fechaFin = CURRENT_TIMESTAMP WHERE u.idInterno = :idInterno AND u.estadoActual = true")
    void cerrarUbicacionActual(@Param("idInterno") Long idInterno);

    @Query("SELECT COUNT(u) FROM InternoUbicacion u WHERE u.idInstitutoSede = :idSede AND u.estadoActual = true")
    Long countInternosEnSede(@Param("idSede") Long idSede);

    // Para reportes de movimientos
    List<InternoUbicacion> findByIdInternoAndFechaInicioBetween(Long idInterno, java.time.LocalDateTime inicio, java.time.LocalDateTime fin);
}
