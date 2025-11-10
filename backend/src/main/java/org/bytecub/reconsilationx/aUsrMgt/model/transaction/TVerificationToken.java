package org.bytecub.reconsilationx.aUsrMgt.model.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytecub.reconsilationx.aUsrMgt.config.audit.AuditModel;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MUser;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.bytecub.reconsilationx.aUsrMgt.constants.TableNames.VERIFICATION_TOKEN_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = VERIFICATION_TOKEN_TABLE, indexes = {
        @Index(name = "fk_T_VERIFICATION_TOKEN_M_USERS1_idx", columnList = "USER_ID")
})
public class TVerificationToken extends AuditModel {
    @Id
    @SequenceGenerator(name = VERIFICATION_TOKEN_TABLE, allocationSize = 1, sequenceName = VERIFICATION_TOKEN_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = VERIFICATION_TOKEN_TABLE)
    @Column(name = "VERIFICATION_ID")
    private Long verificationId;

    @Column(name = "VERIFICATION_TOKEN")
    private String verificationToken;

    @Column(name = "EXPIRY_DATE")
    private LocalDateTime expiryDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    private MUser user;

    public TVerificationToken(MUser user) {
        this.user = user;
        this.verificationToken = UUID.randomUUID().toString();
        this.expiryDate = LocalDateTime.now().plusHours(12); // Token expires in 12 hours
    }
}
