package org.bytecub.reconsilationx.aUsrMgt.service.master;

import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MMenuDto;

import java.util.List;

public interface MenuService {
    ResponseDto createMenu(MMenuDto menuBody);

    List<MMenuDto> getAllMenus();
}
