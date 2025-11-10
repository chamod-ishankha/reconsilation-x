package org.bytecub.reconsilationx.aUsrMgt.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MUserDto;
import org.bytecub.reconsilationx.aUsrMgt.model.transaction.TLoginHistory;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link TLoginHistory}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TLoginHistoryDto implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long loginHId;
    private MUserDto user;
    private String ipAddress;
    private String device;
    private LocalDateTime loginTime;

    private Long userId;
}