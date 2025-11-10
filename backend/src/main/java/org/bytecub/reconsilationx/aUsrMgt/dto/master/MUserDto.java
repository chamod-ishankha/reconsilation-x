package org.bytecub.reconsilationx.aUsrMgt.dto.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RStatusDto;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MUserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String uniqKey;
    private LocalDate dob;
    private Boolean isActive;
    private Boolean isVerified;
    private Boolean isLocked = false;
    private Integer failedAttempts = 0;
    private RStatusDto status;
    private MCompanyDto company;
    private MCompanyBranchDto branch;

    private Long statusId;
    private Long companyId;
    private Long branchId;
}
