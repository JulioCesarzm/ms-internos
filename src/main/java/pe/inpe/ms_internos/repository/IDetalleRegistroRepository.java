package pe.inpe.ms_internos.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.inpe.ms_internos.entity.DetalleRegistro;

import java.util.List;

@Repository
public interface IDetalleRegistroRepository extends JpaRepository<DetalleRegistro, Long> {

    List<DetalleRegistro> findByIdRegistro(Long idRegistro);

    void deleteByIdRegistro(Long idRegistro);
}