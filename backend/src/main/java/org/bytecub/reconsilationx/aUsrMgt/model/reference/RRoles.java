package org.bytecub.reconsilationx.aUsrMgt.model.reference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytecub.reconsilationx.aUsrMgt.config.audit.AuditModel;

import static org.bytecub.reconsilationx.aUsrMgt.constants.TableNames.ROLE_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ROLE_TABLE)
public class RRoles extends AuditModel {
    @Id
    @SequenceGenerator(name = ROLE_TABLE, allocationSize = 1, sequenceName = ROLE_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = ROLE_TABLE)
    @Column(name = "ROLE_ID")
    private Long roleId;
    @Column(name = "ROLE_NAME", length = 50, nullable = false, unique = true)
    private String roleName;
    @Column(name = "ROLE_DESCRIPTION")
    private String roleDescription;
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;
}
