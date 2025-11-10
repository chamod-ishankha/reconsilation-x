package org.bytecub.reconsilationx.aUsrMgt.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RStatusDto;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private String uniqKey;
    private LocalDate dob;
    private TokenDto tokenDto;
    private RStatusDto whrStatus;
    private MCompanyDto companyDto;
}
