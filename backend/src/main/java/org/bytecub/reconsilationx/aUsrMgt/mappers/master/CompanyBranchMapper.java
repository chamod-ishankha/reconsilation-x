package org.bytecub.reconsilationx.aUsrMgt.mappers.master;

import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyBranchDto;
import org.bytecub.reconsilationx.aUsrMgt.mappers.reference.StatusMapper;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MCompanyBranch;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyBranchMapper {

    @Mappings({
            @Mapping(target = "status.statusId", source = "statusId"),
            @Mapping(target = "company.companyId", source = "companyId"),
    })
    MCompanyBranch toEntity(MCompanyBranchDto MCompanyBranchDto);

    @Mappings({
            @Mapping(source = "status.statusId", target = "statusId"),
            @Mapping(source = "company.companyId", target = "companyId"),
    })
    MCompanyBranchDto toDto(MCompanyBranch MCompanyBranch);

    @Mappings({
            @Mapping(source = "status.statusId", target = "statusId"),
            @Mapping(source = "company.companyId", target = "companyId"),
    })
    List<MCompanyBranchDto> listToDto(List<MCompanyBranch> MCompanyBranchList);
}