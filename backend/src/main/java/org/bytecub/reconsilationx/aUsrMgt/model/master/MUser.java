package org.bytecub.reconsilationx.aUsrMgt.model.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytecub.reconsilationx.aUsrMgt.config.audit.AuditModel;
import org.bytecub.reconsilationx.aUsrMgt.model.reference.RStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.bytecub.reconsilationx.aUsrMgt.constants.TableNames.USER_TABLE;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = USER_TABLE, indexes = {
        @Index(name = "UNIQUE_M_USERS_UNIQ_KEY1_idx", columnList = "UNIQ_KEY", unique = true),
        @Index(name = "fk_M_USERS_R_STATUS1_idx", columnList = "STATUS_ID")
})
public class MUser extends AuditModel implements UserDetails {
    @Id
    @SequenceGenerator(name = USER_TABLE, allocationSize = 1, sequenceName = USER_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = USER_TABLE)
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "UNIQ_KEY")
    private String uniqKey;
    @Column(name = "DOB")
    private LocalDate dob;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    @Column(name = "IS_VERIFIED")
    private Boolean isVerified;
    @Column(name = "IS_LOCKED", nullable = false)
    private Boolean isLocked = false;
    @Column(name = "FAILED_ATTEMPTS", nullable = false)
    private Integer failedAttempts;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID", nullable = false)
    private RStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID", nullable = false)
    private MCompany company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRANCH_ID", referencedColumnName = "BRANCH_ID", nullable = false)
    private MCompanyBranch branch;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MUserRole> userRoles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
            .filter(MUserRole::getIsActive) // optional: only active roles
            .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRoles().getRoleName()))
            .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !Boolean.TRUE.equals(isLocked);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(String roleName) {
        if (userRoles == null) return false;
        return userRoles.stream()
            .filter(MUserRole::getIsActive)
            .anyMatch(userRole ->
                userRole.getRoles().getRoleName().equalsIgnoreCase(roleName));
    }

}
