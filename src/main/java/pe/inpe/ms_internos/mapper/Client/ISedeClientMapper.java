package pe.inpe.ms_internos.mapper.Client;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.inpe.ms_internos.dto.SedeResponseDTO;
import pe.inpe.ms_internos.dto.SedeResumenDTO;

@Mapper(componentModel = "spring")
public interface ISedeClientMapper {

    @Mapping(target = "idInstitutoSede", source = "idInstitutoSede")
    @Mapping(target = "nombreSede", source = "nombre")
    @Mapping(target = "capacidad", source = "capacidad")
    default SedeResumenDTO toResumenDTO(SedeResponseDTO response) {
        if (response == null) return null;

        return SedeResumenDTO.builder()
                .idInstitutoSede(response.getIdInstitutoSede())
                .nombreSede(response.getNombre())
                .capacidad(response.getCapacidad())
                .build();
    }
}