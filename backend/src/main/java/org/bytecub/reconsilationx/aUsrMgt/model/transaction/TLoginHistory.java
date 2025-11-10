package org.bytecub.reconsilationx.aUsrMgt.model.transaction;

import jakarta.persistence.*;
import lombok.*;
import org.bytecub.reconsilationx.aUsrMgt.config.audit.AuditModel;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MUser;

import java.time.LocalDateTime;

import static org.bytecub.reconsilationx.aUsrMgt.constants.TableNames.LOGIN_HISTORY_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = LOGIN_HISTORY_TABLE, indexes = {
        @Index(name = "fk_T_LOGIN_HISTORY_M_USERS_idx", columnList = "USER_ID")
})
public class TLoginHistory extends AuditModel {

    @Id
    @SequenceGenerator(name = LOGIN_HISTORY_TABLE, allocationSize = 1, sequenceName = LOGIN_HISTORY_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = LOGIN_HISTORY_TABLE)
    @Column(name = "LOGIN_H_ID")
    private Long loginHId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private MUser user;

    @Column(name = "IP_ADDRESS", length = 50)
    private String ipAddress;

    @Column(name = "DEVICE", length = 255)
    private String device;

    @Column(name = "LOGIN_TIME", nullable = false)
    private LocalDateTime loginTime = LocalDateTime.now();
}

