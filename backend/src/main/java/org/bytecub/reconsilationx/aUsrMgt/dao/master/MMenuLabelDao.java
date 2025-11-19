package org.bytecub.reconsilationx.aUsrMgt.dao.master;

import org.bytecub.reconsilationx.aUsrMgt.model.master.MMenuLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MMenuLabelDao extends JpaRepository<MMenuLabel, Long>, JpaSpecificationExecutor<MMenuLabel> {
}