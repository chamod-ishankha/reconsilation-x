package org.bytecub.reconsilationx.aUsrMgt.model.master;

import jakarta.persistence.*;
import lombok.*;
import org.bytecub.reconsilationx.aUsrMgt.config.audit.AuditModel;
import org.bytecub.reconsilationx.aUsrMgt.model.reference.RStatus;

import static org.bytecub.reconsilationx.aUsrMgt.constants.TableNames.COMPANY_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = COMPANY_TABLE, indexes = {
        @Index(name = "fk_M_COMPANY_R_STATUS1_idx", columnList = "STATUS_ID")
})
public class MCompany extends AuditModel {
    @Id
    @SequenceGenerator(name = COMPANY_TABLE, allocationSize = 1, sequenceName = COMPANY_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = COMPANY_TABLE)
    @Column(name = "COMPANY_ID", nullable = false)
    private Long companyId;
    @Column(name = "NAME", length = 100, nullable = false)
    private String name;
    @Column(name = "EMAIL", length = 100, unique = true, nullable = false)
    private String email;
    @Column(name = "CONTACT_NO", length = 15)
    private String contactNo;
    @Column(name = "ADDRESS", length = 1000, columnDefinition = "TEXT")
    private String address;
    @Column(name = "WEBSITE", length = 255)
    private String website;
    @Column(name = "COMPANY_DESC")
    private String companyDescription;
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;
    @Column(name = "MAX_FAILED_ATTEMPTS")
    private Integer maxFailedLoginAttempts;
    @Column(name = "ENABLE_EMAIL_VERIFICATION")
    private Boolean enableEmailVerification;
    @Column(name = "ALLOW_LEAVE_REQUESTS")
    private Boolean allowLeaveRequests;
    @Column(name = "COMPANY_DEFAULT_LEAVE_REQUEST_COUNT")
    private Integer companyDefaultLeaveRequestCount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID", nullable = false)
    private RStatus status;
}
