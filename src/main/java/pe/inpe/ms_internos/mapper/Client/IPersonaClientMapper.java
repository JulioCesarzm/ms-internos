package pe.inpe.ms_internos.mapper.Client;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pe.inpe.ms_internos.dto.PersonaResponseDTO;
import pe.inpe.ms_internos.dto.PersonaResumenDTO;

@Mapper(componentModel = "spring")
public interface IPersonaClientMapper {

    @Mapping(target = "nombreCompleto", source = ".", qualifiedByName = "buildNombreCompleto")
    @Mapping(target = "tipoDocumento", source = "tipoDocumentoId", qualifiedByName = "mapTipoDocumento")
    PersonaResumenDTO toResumenDTO(PersonaResponseDTO response);

    @Named("buildNombreCompleto")
    default String buildNombreCompleto(PersonaResponseDTO response) {
        String nombreCompleto = response.getNombres() + " " + response.getApellidoPaterno();
        if (response.getApellidoMaterno() != null && !response.getApellidoMaterno().isBlank()) {
            nombreCompleto += " " + response.getApellidoMaterno();
        }
        return nombreCompleto;
    }

    @Named("mapTipoDocumento")
    default String mapTipoDocumento(Long tipoDocumentoId) {
        if (tipoDocumentoId == null) return "No especificado";
        return switch (tipoDocumentoId.intValue()) {
            case 1 -> "DNI";
            case 2 -> "Pasaporte";
            case 3 -> "Carnet de Extranjería";
            case 4 -> "Partida de Nacimiento";
            default -> "Otro";
        };
    }
}