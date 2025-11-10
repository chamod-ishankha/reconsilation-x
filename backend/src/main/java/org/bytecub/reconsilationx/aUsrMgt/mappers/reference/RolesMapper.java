package org.bytecub.reconsilationx.aUsrMgt.mappers.reference;

import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RRolesDto;
import org.bytecub.reconsilationx.aUsrMgt.model.reference.RRoles;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolesMapper {
    RRoles toEntity(RRolesDto RRolesDto);

    RRolesDto toDto(RRoles RRoles);

    List<RRolesDto> listToDto(List<RRoles> RRolesList);
}