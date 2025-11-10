package org.bytecub.reconsilationx.aUsrMgt.mappers.reference;

import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RStatusDto;
import org.bytecub.reconsilationx.aUsrMgt.model.reference.RStatus;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatusMapper {

    RStatusDto toDto(RStatus status);

    RStatus toEntity(RStatusDto status);

    List<RStatusDto> listToDto(List<RStatus> RStatusList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RStatus partialUpdate(RStatusDto RStatusDto, @MappingTarget RStatus RStatus);
}
