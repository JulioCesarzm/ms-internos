package pe.inpe.ms_internos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.inpe.ms_internos.util.auditoria.AuditModel;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro_interno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroInterno extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Long idRegistro;

    @Column(name = "id_interno")
    private Long idInterno;

    @Column(name = "id_instituto_sede")
    private Long idInstitutoSede;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "tipo_registro_id")
    private Long tipoRegistroId;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}