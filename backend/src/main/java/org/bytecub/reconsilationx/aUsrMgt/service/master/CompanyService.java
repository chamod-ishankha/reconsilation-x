package org.bytecub.reconsilationx.aUsrMgt.service.master;

import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;

import java.util.List;

public interface CompanyService {
    MCompanyDto getCompanyById(Long companyId);

    ApiResponseDto<List<MCompanyDto>> getCompanies(int page, Integer perPage, String search, String sort, String direction);

    MCompanyDto updateCompany(Long companyId, MCompanyDto companyDto);

    ResponseDto companyDismiss(Long companyId);
}
