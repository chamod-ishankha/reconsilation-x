package org.bytecub.reconsilationx.aUsrMgt.service.master;

import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyBranchDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;

import java.util.List;

public interface BranchService {
    MCompanyBranchDto getBranchById(Long branchId);

    ApiResponseDto<List<MCompanyBranchDto>> getBranches(int page, Integer perPage, String search, String sort, String direction, Long companyId);

    MCompanyBranchDto updateBranch(Long branchId, MCompanyBranchDto branchDto);

    ResponseDto deleteBranch(Long branchId);

    ResponseDto createBranch(MCompanyBranchDto branchDto);

    ResponseDto assignBranchToUser(Long branchId, Long userId);
}
