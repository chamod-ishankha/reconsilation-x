package org.bytecub.reconsilationx.aUsrMgt.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MUserDto;
import org.bytecub.reconsilationx.aUsrMgt.model.transaction.TPasswordResetToken;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link TPasswordResetToken}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TPasswordResetTokenDto implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long resetTokenId;
    private String otp;
    private LocalDateTime expiresAt;
    private MUserDto user;

    private Long userId;
}