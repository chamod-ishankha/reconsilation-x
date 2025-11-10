package org.bytecub.reconsilationx.aUsrMgt.dto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.aUsrMgt.model.reference.RRoles;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link RRoles}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RRolesDto implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long roleId;
    private String roleName;
    private String roleDescription;
    private Boolean isActive;
}