package pe.inpe.ms_internos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.inpe.ms_internos.util.auditoria.AuditModel;

@Entity
@Table(name = "detalle_registro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleRegistro extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @Column(name = "id_registro")
    private Long idRegistro;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "valor")
    private String valor;
}