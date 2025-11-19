package org.bytecub.reconsilationx.aUsrMgt.dao.master;

import org.bytecub.reconsilationx.aUsrMgt.model.master.MMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MMenuDao extends JpaRepository<MMenu, Long>, JpaSpecificationExecutor<MMenu> {
    List<MMenu> findByParentIsNull();
}