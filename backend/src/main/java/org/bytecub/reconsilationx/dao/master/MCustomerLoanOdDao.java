package org.bytecub.reconsilationx.dao.master;

import org.bytecub.reconsilationx.model.master.MCustomerLoanOd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MCustomerLoanOdDao extends JpaRepository<MCustomerLoanOd, Long>, JpaSpecificationExecutor<MCustomerLoanOd> {
}