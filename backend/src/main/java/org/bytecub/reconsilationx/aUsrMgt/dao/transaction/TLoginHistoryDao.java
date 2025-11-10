package org.bytecub.reconsilationx.aUsrMgt.dao.transaction;

import org.bytecub.reconsilationx.aUsrMgt.model.transaction.TLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TLoginHistoryDao extends JpaRepository<TLoginHistory, Long>, JpaSpecificationExecutor<TLoginHistory> {
}