package org.bytecub.reconsilationx.aUsrMgt.dao.reference;

import org.bytecub.reconsilationx.aUsrMgt.model.reference.RRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RRolesDao extends JpaRepository<RRoles, Long>, JpaSpecificationExecutor<RRoles> {
    Optional<RRoles> findByRoleNameAndIsActive(String roleName, boolean b);

    boolean existsByRoleIdAndIsActive(Long roleId, boolean b);
}