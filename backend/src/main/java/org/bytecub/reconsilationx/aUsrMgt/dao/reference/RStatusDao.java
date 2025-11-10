package org.bytecub.reconsilationx.aUsrMgt.dao.reference;

import org.bytecub.reconsilationx.aUsrMgt.model.reference.RStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RStatusDao extends JpaRepository<RStatus, Long>, JpaSpecificationExecutor<RStatus> {
    Optional<RStatus> findByStatusIdAndIsActive(Long statusId, boolean isActive);

    Optional<RStatus> findByStatusName(String aNew);
}
