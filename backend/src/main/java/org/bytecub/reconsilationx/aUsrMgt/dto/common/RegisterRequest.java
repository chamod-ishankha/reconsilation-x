package org.bytecub.reconsilationx.aUsrMgt.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty(message = "First name is required")
    @NotNull(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @NotNull(message = "Last name is required")
    private String lastName;
    @NotEmpty(message = "Email is required")
    @NotNull(message = "Email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    @NotNull(message = "Password is required")
    private String password;
    private String phone;
    @JsonIgnore
    private String role;
    private String status;
    @NotNull(message = "Company ID is required")
    private Long companyId;
    @NotNull(message = "Branch ID is required")
    private Long branchId;
    private LocalDate dob;
}
