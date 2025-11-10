package org.bytecub.reconsilationx.aUsrMgt.mappers.master;

import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyDto;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MCompany;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mappings({
            @Mapping(target = "status.statusId", source = "statusId"),
    })
    MCompany toEntity(MCompanyDto MCompanyDto);

    @Mappings({
            @Mapping(source = "status.statusId", target = "statusId"),
    })
    MCompanyDto toDto(MCompany MCompany);

    @Mappings({
            @Mapping(source = "status.statusId", target = "statusId"),
    })
    List<MCompanyDto> listToDto(List<MCompany> MCompanyList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MCompany partialUpdate(MCompanyDto MCompanyDto, @MappingTarget MCompany MCompany);
}