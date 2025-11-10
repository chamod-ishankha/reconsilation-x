package org.bytecub.reconsilationx.aUsrMgt.service.reference;

import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RStatusDto;

import java.util.List;

public interface StatusService {
    RStatusDto createStatus(RStatusDto statusDto);

    ApiResponseDto<List<RStatusDto>> listStatus(int page, Integer perPage, String search, String sort, String direction);

    RStatusDto getStatusById(Long statusId);

    ResponseDto updateStatus(Long statusId, RStatusDto statusDto);

    ResponseDto deleteStatus(Long statusId);
}
