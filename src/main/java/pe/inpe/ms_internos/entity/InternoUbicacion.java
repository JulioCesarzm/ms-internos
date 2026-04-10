package pe.inpe.ms_internos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.inpe.ms_internos.util.auditoria.AuditModel;

import java.time.LocalDateTime;

@Entity
@Table(name = "interno_ubicacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternoUbicacion extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Long idUbicacion;

    @Column(name = "id_interno")
    private Long idInterno;

    @Column(name = "id_instituto_sede")
    private Long idInstitutoSede;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "estado_actual")
    private Boolean estadoActual;
}
