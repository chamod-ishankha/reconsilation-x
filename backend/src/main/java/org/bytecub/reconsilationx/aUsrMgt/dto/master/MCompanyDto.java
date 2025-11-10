package org.bytecub.reconsilationx.aUsrMgt.dto.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RStatusDto;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MCompany;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link MCompany}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCompanyDto implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long companyId;
    private String name;
    private String email;
    private String contactNo;
    private String address;
    private String website;
    private String companyDescription;
    private Boolean isActive;
    private Integer maxFailedLoginAttempts = 5;
    private Boolean enableEmailVerification = true;
    private Boolean allowLeaveRequests = true;
    private Integer companyDefaultLeaveRequestCount = 21;
    private RStatusDto status;

    private Long statusId;
}