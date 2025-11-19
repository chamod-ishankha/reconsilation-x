package org.bytecub.reconsilationx.aUsrMgt.dao.master;

import jakarta.validation.constraints.NotNull;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MCompanyDao extends JpaRepository<MCompany, Long>, JpaSpecificationExecutor<MCompany> {
    Optional<MCompany> findByCompanyIdAndIsActive(Long companyId, boolean b);

    Optional<MCompany> findByEmailAndIsActive(String companyEmail, boolean b);
}