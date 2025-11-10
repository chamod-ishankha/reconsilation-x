package org.bytecub.reconsilationx.aUsrMgt.mappers.master;

import org.bytecub.reconsilationx.aUsrMgt.dto.master.MUserRoleDto;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MUserRole;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    @Mappings({
            @Mapping(target = "user.userId", source = "userId"),
            @Mapping(target = "roles.roleId", source = "roleId"),
            @Mapping(target = "status.statusId", source = "statusId"),
    })
    MUserRole toEntity(MUserRoleDto MUserRoleDto);

    @Mappings({
            @Mapping(source = "user.userId", target = "userId"),
            @Mapping(source = "roles.roleId", target = "roleId"),
            @Mapping(source = "status.statusId", target = "statusId"),
    })
    MUserRoleDto toDto(MUserRole MUserRole);

    @Mappings({
            @Mapping(source = "user.userId", target = "userId"),
            @Mapping(source = "roles.roleId", target = "roleId"),
            @Mapping(source = "status.statusId", target = "statusId"),
    })
    List<MUserRoleDto> listToDto(List<MUserRole> MUserRoles);
}