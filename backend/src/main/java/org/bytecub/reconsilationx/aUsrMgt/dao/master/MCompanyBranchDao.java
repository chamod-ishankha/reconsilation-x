package org.bytecub.reconsilationx.aUsrMgt.dao.master;

import jakarta.validation.constraints.NotNull;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MCompanyBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MCompanyBranchDao extends JpaRepository<MCompanyBranch, Long>, JpaSpecificationExecutor<MCompanyBranch> {
  Optional<MCompanyBranch> findByBranchIdAndIsActive(Long branchId, boolean b);
}