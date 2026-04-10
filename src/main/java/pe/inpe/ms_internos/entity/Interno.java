package pe.inpe.ms_internos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.inpe.ms_internos.util.auditoria.AuditModel;

import java.time.LocalDate;

@Entity
@Table(name = "interno",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_interno_codigo_interno", columnNames = "codigo_interno"),
                @UniqueConstraint(name = "uq_interno_id_persona", columnNames = "id_persona")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Interno extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_interno")
    private Long idInterno;

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "codigo_interno")
    private String codigoInterno;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "estado")
    private Boolean estado;
}