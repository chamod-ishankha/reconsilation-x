package org.bytecub.reconsilationx.aUsrMgt.dto.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RStatusDto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.bytecub.reconsilationx.aUsrMgt.model.master.MCompanyBranch}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCompanyBranchDto implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long branchId;
    private String branchCode;
    private String branchName;
    private String branchAddress;
    private String branchContact1;
    private String branchContact2;
    private String branchEmail;
    private MCompanyDto company;
    private RStatusDto status;
    private Boolean isActive;

    private Long companyId;
    private Long statusId;
}