package org.bytecub.reconsilationx.aUsrMgt.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequestDto {
    private String yourEmail;
    private String companyEmail;
}
