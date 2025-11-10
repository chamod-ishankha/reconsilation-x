package org.bytecub.reconsilationx.aUsrMgt.model.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytecub.reconsilationx.aUsrMgt.config.audit.AuditModel;
import org.bytecub.reconsilationx.aUsrMgt.model.reference.RStatus;

import static org.bytecub.reconsilationx.aUsrMgt.constants.TableNames.COMPANY_BRANCH_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = COMPANY_BRANCH_TABLE,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"COMPANY_ID", "BRANCH_CODE"})
        })
public class MCompanyBranch extends AuditModel {

    @Id
    @SequenceGenerator(name = COMPANY_BRANCH_TABLE, allocationSize = 1, sequenceName = COMPANY_BRANCH_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = COMPANY_BRANCH_TABLE)
    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "BRANCH_CODE", nullable = false, length = 6)
    private String branchCode;

    @Column(name = "BRANCH_NAME", nullable = false, length = 100)
    private String branchName;

    @Column(name = "BRANCH_ADDRESS", length = 250)
    private String branchAddress;

    @Column(name = "BRANCH_CONTACT_1", length = 15)
    private String branchContact1;

    @Column(name = "BRANCH_CONTACT_2", length = 15)
    private String branchContact2;

    @Column(name = "BRANCH_EMAIL", length = 50)
    private String branchEmail;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID", nullable = false)
    private MCompany company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID", nullable = false)
    private RStatus status;

}
