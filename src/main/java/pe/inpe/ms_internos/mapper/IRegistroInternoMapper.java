package pe.inpe.ms_internos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.inpe.ms_internos.dto.RegistroInternoRequestDTO;
import pe.inpe.ms_internos.dto.RegistroInternoResponseDTO;
import pe.inpe.ms_internos.entity.RegistroInterno;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IDetalleRegistroMapper.class})
public interface IRegistroInternoMapper {

    @Mapping(target = "idRegistro", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "registrationUser", ignore = true)
    @Mapping(target = "lastModificationDate", ignore = true)
    @Mapping(target = "lastModificationUser", ignore = true)
    RegistroInterno toEntity(RegistroInternoRequestDTO request);

    RegistroInternoResponseDTO toResponse(RegistroInterno entity);

    List<RegistroInternoResponseDTO> toResponseList(List<RegistroInterno> entities);
}