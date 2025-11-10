package org.bytecub.reconsilationx.aUsrMgt.dto.common;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CRegistrationRequest {
    // company details
    @NotEmpty(message = "Company name is required.")
    @NotNull(message = "Company name is required.")
    private String companyName;
    @NotEmpty(message = "Company email is required.")
    @NotNull(message = "Company email is required.")
    private String companyEmail;
    private String companyContactNo;
    private String companyAddress;
    private String companyWebsite;
    private String companyDescription;

    // admin details
    @NotEmpty(message = "First name is required.")
    @NotNull(message = "First name is required.")
    private String adminFirstName;
    @NotEmpty(message = "Last name is required.")
    @NotNull(message = "Last name is required.")
    private String adminLastName;
    @NotEmpty(message = "Email is required.")
    @NotNull(message = "Email is required.")
    private String adminEmail;
    @NotEmpty(message = "Password is required.")
    @NotNull(message = "Password is required.")
    private String adminPassword;
    private String adminPhone;
}
