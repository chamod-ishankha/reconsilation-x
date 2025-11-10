package org.bytecub.reconsilationx.aUsrMgt.model.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytecub.reconsilationx.aUsrMgt.config.audit.AuditModel;
import org.bytecub.reconsilationx.aUsrMgt.model.reference.RRoles;
import org.bytecub.reconsilationx.aUsrMgt.model.reference.RStatus;

import static org.bytecub.reconsilationx.aUsrMgt.constants.TableNames.USER_ROLE_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = USER_ROLE_TABLE, indexes = {
        @Index(name = "fk_M_USER_ROLE_M_USER1_idx", columnList = "USER_ID"),
        @Index(name = "fk_M_USER_ROLE_R_ROLE1_idx", columnList = "ROLE_ID"),
        @Index(name = "fk_M_USER_ROLE_R_STATUS1_idx", columnList = "STATUS_ID"),
})
public class MUserRole extends AuditModel {
    @Id
    @SequenceGenerator(name = USER_ROLE_TABLE, allocationSize = 1, sequenceName = USER_ROLE_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = USER_ROLE_TABLE)
    @Column(name = "USER_ROLE_ID")
    private Long userRoleId;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    private MUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID", nullable = false)
    private RRoles roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID", nullable = false)
    private RStatus status;
}
