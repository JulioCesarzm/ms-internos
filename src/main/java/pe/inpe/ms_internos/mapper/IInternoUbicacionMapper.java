package pe.inpe.ms_internos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.inpe.ms_internos.dto.InternoUbicacionRequestDTO;
import pe.inpe.ms_internos.dto.InternoUbicacionResponseDTO;
import pe.inpe.ms_internos.entity.InternoUbicacion;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IInternoUbicacionMapper {

    @Mapping(target = "idUbicacion", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "registrationUser", ignore = true)
    @Mapping(target = "lastModificationDate", ignore = true)
    @Mapping(target = "lastModificationUser", ignore = true)
    InternoUbicacion toEntity(InternoUbicacionRequestDTO request);

    InternoUbicacionResponseDTO toResponse(InternoUbicacion entity);

    List<InternoUbicacionResponseDTO> toResponseList(List<InternoUbicacion> entities);
}