package pe.inpe.ms_internos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.inpe.ms_internos.util.auditoria.AuditModel;

import java.time.LocalDate;

@Entity
@Table(name = "clasificacion_interno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClasificacionInterno extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clasificacion")
    private Long idClasificacion;

    @Column(name = "id_interno")
    private Long idInterno;

    @Column(name = "nivel_seguridad_id")
    private Long nivelSeguridadId;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
}