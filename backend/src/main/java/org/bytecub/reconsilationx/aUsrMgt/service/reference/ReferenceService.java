package org.bytecub.reconsilationx.aUsrMgt.service.reference;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ReferenceService {

    private final StatusService statusService;
    private final RoleService roleService;

}
