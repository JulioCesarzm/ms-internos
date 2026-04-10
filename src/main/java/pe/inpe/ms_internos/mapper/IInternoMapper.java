package pe.inpe.ms_internos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pe.inpe.ms_internos.dto.InternoRequestDTO;
import pe.inpe.ms_internos.dto.InternoResponseDTO;
import pe.inpe.ms_internos.entity.Interno;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IInternoMapper {

    @Mapping(target = "idInterno", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "registrationUser", ignore = true)
    @Mapping(target = "lastModificationDate", ignore = true)
    @Mapping(target = "lastModificationUser", ignore = true)
    Interno toEntity(InternoRequestDTO request);

    InternoResponseDTO toResponse(Interno entity);

    List<InternoResponseDTO> toResponseList(List<Interno> entities);

    @Mapping(target = "idInterno", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "registrationUser", ignore = true)
    @Mapping(target = "lastModificationDate", ignore = true)
    @Mapping(target = "lastModificationUser", ignore = true)
    void updateEntity(@MappingTarget Interno entity, InternoRequestDTO request);
}