package org.bytecub.reconsilationx.aUsrMgt.mappers.master;

import org.bytecub.reconsilationx.aUsrMgt.dto.master.MUserDto;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "statusId", source = "status.statusId"),
            @Mapping(target = "companyId", source = "company.companyId"),
            @Mapping(target = "branchId", source = "branch.branchId"),
    })
    MUserDto toDto(MUser user);

    @Mappings({
            @Mapping(source = "statusId", target = "status.statusId"),
            @Mapping(source = "companyId", target = "company.companyId"),
            @Mapping(source = "branchId", target = "branch.branchId"),
    })
    MUser toEntity(MUserDto userDto);

    @Mappings({
            @Mapping(target = "statusId", source = "status.statusId"),
            @Mapping(target = "companyId", source = "company.companyId"),
            @Mapping(target = "branchId", source = "branch.branchId"),
    })
    List<MUserDto> listToDto(List<MUser> users);
}
