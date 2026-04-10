package pe.inpe.ms_internos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.inpe.ms_internos.dto.DetalleRegistroRequestDTO;
import pe.inpe.ms_internos.dto.DetalleRegistroResponseDTO;
import pe.inpe.ms_internos.entity.DetalleRegistro;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDetalleRegistroMapper {

    @Mapping(target = "idDetalle", ignore = true)
    @Mapping(target = "idRegistro", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "registrationUser", ignore = true)
    @Mapping(target = "lastModificationDate", ignore = true)
    @Mapping(target = "lastModificationUser", ignore = true)
    DetalleRegistro toEntity(DetalleRegistroRequestDTO request);

    DetalleRegistroResponseDTO toResponse(DetalleRegistro entity);

    List<DetalleRegistroResponseDTO> toResponseList(List<DetalleRegistro> entities);
}