package org.bytecub.reconsilationx.aUsrMgt.dao.master;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MUserDao extends JpaRepository<MUser, Long>, JpaSpecificationExecutor<MUser> {
    Optional<MUser> findByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsByUniqKey(String uniqKey);

    Optional<MUser> findByEmailAndCompanyCompanyId(String email, Long companyId);

    boolean existsByUserIdAndIsActive(Long userId, boolean b);

    boolean existsByEmailAndCompanyEmailAndIsActive(String email, String companyId, boolean b);

    Optional<MUser> findByEmailAndCompanyEmailAndIsActive(String email, String companyEmail, boolean b);

    boolean existsByEmailAndCompanyCompanyIdAndBranchBranchIdAndIsActive(String email, Long companyId, Long branchId, boolean b);

    Optional<MUser> findByUserIdAndIsActive(Long userId, boolean b);

    Optional<MUser> findByEmailAndCompanyEmail(String email, String companyEmail);
}
