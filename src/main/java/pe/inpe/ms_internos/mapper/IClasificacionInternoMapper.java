package pe.inpe.ms_internos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.inpe.ms_internos.dto.ClasificacionInternoRequestDTO;
import pe.inpe.ms_internos.dto.ClasificacionInternoResponseDTO;
import pe.inpe.ms_internos.entity.ClasificacionInterno;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IClasificacionInternoMapper {

    @Mapping(target = "idClasificacion", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "registrationUser", ignore = true)
    @Mapping(target = "lastModificationDate", ignore = true)
    @Mapping(target = "lastModificationUser", ignore = true)
    ClasificacionInterno toEntity(ClasificacionInternoRequestDTO request);

    ClasificacionInternoResponseDTO toResponse(ClasificacionInterno entity);

    List<ClasificacionInternoResponseDTO> toResponseList(List<ClasificacionInterno> entities);
}