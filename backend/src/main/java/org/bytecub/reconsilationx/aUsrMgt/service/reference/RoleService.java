package org.bytecub.reconsilationx.aUsrMgt.service.reference;

import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RRolesDto;

public interface RoleService {
    ResponseDto assignRoleToUser(Long userId, Long roleId);

    RRolesDto getRoleByRoleName(String roleName);
}
