package pe.inpe.ms_internos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.inpe.ms_internos.entity.Interno;

import java.util.List;
import java.util.Optional;

@Repository
public interface IInternoRepository extends JpaRepository<Interno, Long> {

    Optional<Interno> findByCodigoInterno(String codigoInterno);

    Optional<Interno> findByIdPersona(Long idPersona);

    List<Interno> findByEstado(Boolean estado);

    boolean existsByCodigoInterno(String codigoInterno);

    boolean existsByIdPersona(Long idPersona);

    @Query("SELECT i FROM Interno i WHERE i.estado = true")
    List<Interno> findAllActivos();

    @Query("SELECT i FROM Interno i WHERE i.idPersona IN :idsPersona")
    List<Interno> findByIdsPersona(@Param("idsPersona") List<Long> idsPersona);
}