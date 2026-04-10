package pe.inpe.ms_internos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.inpe.ms_internos.entity.ClasificacionInterno;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClasificacionInternoRepository extends JpaRepository<ClasificacionInterno, Long> {

    List<ClasificacionInterno> findByIdInternoOrderByFechaInicioDesc(Long idInterno);

    @Query("SELECT c FROM ClasificacionInterno c WHERE c.idInterno = :idInterno AND c.fechaFin IS NULL")
    Optional<ClasificacionInterno> findClasificacionActivaByInterno(@Param("idInterno") Long idInterno);

    List<ClasificacionInterno> findByNivelSeguridadId(Long nivelSeguridadId);

    @Modifying
    @Query("UPDATE ClasificacionInterno c SET c.fechaFin = CURRENT_DATE WHERE c.idInterno = :idInterno AND c.fechaFin IS NULL")
    void cerrarClasificacionActiva(@Param("idInterno") Long idInterno);

    @Query("SELECT c FROM ClasificacionInterno c WHERE c.nivelSeguridadId = :nivelSeguridadId AND c.fechaFin IS NULL")
    List<ClasificacionInterno> findInternosPorNivelSeguridadActivo(@Param("nivelSeguridadId") Long nivelSeguridadId);
}