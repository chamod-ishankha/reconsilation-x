package org.bytecub.reconsilationx.aUsrMgt.dto.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RRolesDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RStatusDto;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MUserRole;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link MUserRole}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MUserRoleDto implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long userRoleId;
    private Boolean isActive;
    private MUserDto user;
    private RRolesDto roles;
    private RStatusDto status;

    private Long userId;
    private Long roleId;
    private Long statusId;
}