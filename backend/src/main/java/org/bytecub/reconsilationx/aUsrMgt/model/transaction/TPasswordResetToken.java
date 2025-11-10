package org.bytecub.reconsilationx.aUsrMgt.model.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytecub.reconsilationx.aUsrMgt.config.audit.AuditModel;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MUser;

import java.time.LocalDateTime;

import static org.bytecub.reconsilationx.aUsrMgt.constants.TableNames.PASSWORD_RESET_TOKEN_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = PASSWORD_RESET_TOKEN_TABLE, indexes = {
        @Index(name = "fk_T_PASSWORD_RESET_TOKEN_M_USERS1_idx", columnList = "USER_ID")
})
public class TPasswordResetToken extends AuditModel {

    @Id
    @SequenceGenerator(name = PASSWORD_RESET_TOKEN_TABLE, allocationSize = 1, sequenceName = PASSWORD_RESET_TOKEN_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = PASSWORD_RESET_TOKEN_TABLE)
    @Column(name = "RESET_TOKEN_ID")
    private Long resetTokenId;

    @Column(name = "OTP")
    private String otp;

    @Column(name = "EXPIRES_AT")
    private LocalDateTime expiresAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    private MUser user;

}
